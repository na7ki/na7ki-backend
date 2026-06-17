import librosa
import numpy as np

def check_audio(path: str) -> tuple[bool, str]:
    try:
        audio, sr = librosa.load(path, sr=16000)
    except Exception:
        return False, "unreadable"

    # Check duration: must be 0.5s–8s
    duration = len(audio) / sr
    if duration < 0.5:
        return False, "too_short"
    if duration > 8.0:
        return False, "too_long"

    # Check energy: reject silence
    energy = float(np.sqrt(np.mean(audio ** 2)))
    if energy < 0.01:
        return False, "silence"

    return True, "ok"