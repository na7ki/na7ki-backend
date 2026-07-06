from contextlib import asynccontextmanager

from fastapi import FastAPI, UploadFile, File, Form, HTTPException

from app import inference
from app.config import STRICT_CONFIDENCE_THRESHOLD, MODEL_VERSION, VOCAB_SIZE
from app.schemas import VerifyResponse, HealthResponse


@asynccontextmanager
async def lifespan(app: FastAPI):
    # Loads the model once when the process starts, not per-request.
    try:
        inference.load_model(vocab_size=VOCAB_SIZE)
    except FileNotFoundError:
        print("WARNING: checkpoint not found at startup — /verify will fail until it's added to artifacts/.")
    yield


app = FastAPI(title="Arabic Pronunciation AI Service", lifespan=lifespan)


@app.get("/health", response_model=HealthResponse)
def health():
    return HealthResponse(status="ok", model_loaded=inference.is_ready())


@app.post("/verify", response_model=VerifyResponse)
async def verify(audio: UploadFile = File(...), word_id: int = Form(...)):
    if not inference.is_ready():
        raise HTTPException(status_code=503, detail="Model not loaded")

    audio_bytes = await audio.read()
    if not audio_bytes:
        raise HTTPException(status_code=400, detail="Empty audio upload")

    probability = inference.predict(audio_bytes, word_id)
    is_correct = probability > STRICT_CONFIDENCE_THRESHOLD

    return VerifyResponse(
        word_id=word_id,
        probability=round(probability, 4),
        is_correct=is_correct,
        threshold_used=STRICT_CONFIDENCE_THRESHOLD,
        model_version=MODEL_VERSION,
    )
