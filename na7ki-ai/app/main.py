import os
import uuid
import shutil
import logging
from contextlib import asynccontextmanager

from fastapi import FastAPI, UploadFile, Form, HTTPException
from fastapi.middleware.cors import CORSMiddleware

from app.models import get_model
from app.audio_checks import check_audio
from app.evaluator import evaluate_result, find_wrong_phonemes
import subprocess

import subprocess
import uuid
import os

# Helper function to convert any input audio to 16kHz mono WAV using ffmpeg
def convert_to_wav(input_path: str) -> str:
    output_path = f"/tmp/{uuid.uuid4()}.wav"
    result = subprocess.run([
        "ffmpeg", "-i", input_path,
        "-ar", "16000",   # 16kHz
        "-ac", "1",       # mono
        "-y",             # overwrite
        output_path
    ], capture_output=True)

    if result.returncode != 0:
        raise ValueError(f"ffmpeg conversion failed: {result.stderr.decode()}")

    return output_path

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Load model at startup
@asynccontextmanager
async def lifespan(app: FastAPI):
    logger.info("Loading Qwen3-ASR model...")
    get_model()
    logger.info("Model ready.")
    yield

app = FastAPI(title="Nahki AI Service", lifespan=lifespan)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

def convert_to_wav(input_path: str) -> str:
    output_path = f"/tmp/{uuid.uuid4()}.wav"
    result = subprocess.run([
        "ffmpeg", "-i", input_path,
        "-ar", "16000", "-ac", "1", "-y", output_path
    ], capture_output=True)
    if result.returncode != 0:
        raise ValueError("Audio conversion failed")
    return output_path


@app.post("/api/v1/evaluate")
async def evaluate_speech(
    audio: UploadFile,
    target: str = Form(...),          # Arabic word/sentence child should say
    task_id: str = Form(...),         # for logging
    patient_id: str = Form(...),      # for logging
):
    raw_path = f"/tmp/{uuid.uuid4()}_{audio.filename}"
    wav_path = None

    try:
        # Save uploaded file
        with open(raw_path, "wb") as f:
            shutil.copyfileobj(audio.file, f)

        # Convert to 16kHz WAV
        wav_path = convert_to_wav(raw_path)

        # Audio quality gate
        valid, reason = check_audio(wav_path)
        if not valid:
            logger.warning(f"Invalid audio from patient {patient_id}: {reason}")
            return {
                "result": "invalid_audio",
                "reason": reason,
                "transcription": None,
                "similarity": None,
                "wrong_phonemes": [],
            }

        # Run ASR + ForcedAligner
        model = get_model()
        results = model.transcribe(
            audio=wav_path,
            language="Arabic",          # force Arabic — uses LID internally
            return_time_stamps=True,    # needed for phoneme-level feedback
        )

        result = results[0]
        transcription = result.text
        detected_language = result.language
        time_stamps = result.time_stamps  # character-level from ForcedAligner

        logger.info(f"Patient {patient_id} | Task {task_id} | "
                    f"Lang: {detected_language} | Transcription: {transcription}")

        # Language gate: reject if not Arabic
        if detected_language and "Arabic" not in detected_language:
            return {
                "result": "invalid_audio",
                "reason": "not_arabic",
                "transcription": transcription,
                "similarity": None,
                "wrong_phonemes": [],
            }

        # Score
        evaluation = evaluate_result(transcription, target)

        # Phoneme-level mismatch (only useful for partial/fail)
        wrong_phonemes = []
        if evaluation["tier"] in ("partial", "fail") and time_stamps:
            wrong_phonemes = find_wrong_phonemes(time_stamps, target)

        return {
            "result": evaluation["tier"],          # success / partial / fail
            "similarity": evaluation["similarity"],
            "transcription": transcription,
            "target": target,
            "wrong_phonemes": wrong_phonemes,      # which characters were wrong
            "task_id": task_id,
            "patient_id": patient_id,
        }

    except Exception as e:
        logger.error(f"Evaluation error: {e}")
        raise HTTPException(status_code=500, detail="AI evaluation failed")

    finally:
        # Always clean up temp files
        for path in [raw_path, wav_path]:
            if path and os.path.exists(path):
                os.remove(path)


@app.get("/health")
def health():
    return {"status": "ok"}