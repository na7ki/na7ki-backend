import json
import torch

from app.model import ArabicPronunciationVerifier
from app.dsp import preprocess_audio_bytes
from app.config import CHECKPOINT_PATH, WORDS_JSON_PATH, VOCAB_SIZE

_model: ArabicPronunciationVerifier | None = None
_calib_a: float = 1.0
_calib_b: float = 0.0
_device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
_word_mapping: dict = {}


def is_ready() -> bool:
    return _model is not None


def load_model(checkpoint_path: str = CHECKPOINT_PATH, vocab_size: int = VOCAB_SIZE) -> None:
    """
    Loads the trained checkpoint once at process startup.
    vocab_size must be >= (max word_id seen in training) + 1 — same value
    used when the model was constructed and trained in the notebook.
    """
    global _model, _calib_a, _calib_b, _word_mapping

    _model = ArabicPronunciationVerifier(vocab_size=vocab_size)
    ckpt = torch.load(checkpoint_path, map_location=_device)
    _model.load_state_dict(ckpt["model_state"])
    _model.eval().to(_device)
    _calib_a = ckpt["calib_a"]
    _calib_b = ckpt["calib_b"]

    try:
        with open(WORDS_JSON_PATH, "r", encoding="utf-8") as f:
            _word_mapping = json.load(f)
    except FileNotFoundError:
        _word_mapping = {}

    print(f"Model loaded on {_device}. calib_a={_calib_a:.3f} calib_b={_calib_b:.3f}")


@torch.no_grad()
def predict(audio_bytes: bytes, word_id: int) -> float:
    """Returns a calibrated probability in [0, 1] that the pronunciation is correct."""
    if _model is None:
        raise RuntimeError("Model not loaded. Call load_model() at startup.")

    mel_spec = preprocess_audio_bytes(audio_bytes)
    mel_tensor = torch.from_numpy(mel_spec).float().unsqueeze(0).unsqueeze(0).to(_device)
    word_tensor = torch.tensor([word_id], dtype=torch.long).to(_device)

    logit = _model(mel_tensor, word_tensor)
    prob = torch.sigmoid(_calib_a * logit + _calib_b).item()
    return prob
