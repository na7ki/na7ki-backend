"""
Signal-processing pipeline. This is the same logic as the notebook's Phase 2,
adapted only so it can read audio from raw bytes (an upload) instead of a
file path on disk. The numeric behavior must stay identical to training.
"""

import io
import numpy as np
import librosa

from app.config import SAMPLE_RATE, N_SAMPLES, N_MELS, N_FFT, HOP_LENGTH, TARGET_FRAMES, TOP_DB


def _fix_waveform_length(y: np.ndarray, target_len: int = N_SAMPLES) -> np.ndarray:
    if len(y) < target_len:
        return np.pad(y, (0, target_len - len(y)), mode="constant")
    return y[:target_len]


def _fix_matrix_frames(matrix: np.ndarray, target_frames: int = TARGET_FRAMES) -> np.ndarray:
    n_mels, t = matrix.shape
    if t < target_frames:
        pad_width = target_frames - t
        return np.pad(matrix, ((0, 0), (0, pad_width)), mode="constant")
    return matrix[:, :target_frames]


def _normalize_unit_interval(matrix: np.ndarray) -> np.ndarray:
    min_val, max_val = float(matrix.min()), float(matrix.max())
    if (max_val - min_val) < 1e-6:
        return np.zeros_like(matrix, dtype=np.float32)
    return ((matrix - min_val) / (max_val - min_val)).astype(np.float32)


def extract_log_mel_spectrogram(y: np.ndarray, sr: int = SAMPLE_RATE) -> np.ndarray:
    mel_power = librosa.feature.melspectrogram(
        y=y, sr=sr, n_fft=N_FFT, hop_length=HOP_LENGTH, n_mels=N_MELS
    )
    mel_db = librosa.power_to_db(mel_power, ref=np.max)
    mel_db = _fix_matrix_frames(mel_db, TARGET_FRAMES)
    return _normalize_unit_interval(mel_db)


def load_waveform_from_bytes(audio_bytes: bytes, sr: int = SAMPLE_RATE) -> np.ndarray | None:
    """
    Loads + resamples + silence-trims an in-memory audio file (wav/m4a/etc).
    Returns None if the bytes can't be decoded at all.
    """
    try:
        y, _ = librosa.load(io.BytesIO(audio_bytes), sr=sr, mono=True)
    except Exception:
        return None

    try:
        y_trimmed, _ = librosa.effects.trim(y, top_db=TOP_DB)
        if len(y_trimmed) > 0:
            y = y_trimmed
    except Exception:
        pass

    return y


def preprocess_audio_bytes(audio_bytes: bytes) -> np.ndarray:
    """
    Full pipeline for one uploaded utterance -> a normalized log-mel spectrogram,
    ready to feed into the model. Falls back to a zero-spectrogram (rather than
    raising) if decoding unexpectedly fails, so a single bad upload doesn't
    500 the request — it'll just come back as very low confidence.
    """
    y = load_waveform_from_bytes(audio_bytes)
    if y is None:
        y = np.zeros(N_SAMPLES, dtype=np.float32)

    y = _fix_waveform_length(y, N_SAMPLES)
    mel_spec = extract_log_mel_spectrogram(y, SAMPLE_RATE)
    return mel_spec
