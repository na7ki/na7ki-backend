"""
Constants copied verbatim from the training notebook.
IMPORTANT: these must never drift from the values used during training,
or the model will see spectrograms shaped differently than it learned on.
"""

import json

SAMPLE_RATE: int = 16_000
DURATION_SEC: float = 2.0
N_SAMPLES: int = int(SAMPLE_RATE * DURATION_SEC)
N_MELS: int = 128
N_FFT: int = 1024
HOP_LENGTH: int = 256
TARGET_FRAMES: int = 128
TOP_DB: int = 20
EMBED_DIM: int = 64

STRICT_CONFIDENCE_THRESHOLD: float = 0.85

CHECKPOINT_PATH: str = "artifacts/verifier_checkpoint.pth"
WORDS_JSON_PATH: str = "artifacts/words_id.json"
MODEL_VERSION: str = "v1.0-dev"

# vocab_size must equal (max word_id seen in training) + 1 — same rule the
# notebook uses (`int(df_clean["word_id"].max() + 1)`). Derived from
# words_id.json rather than hardcoded so it can't silently drift from the
# checkpoint's embedding table size.
with open(WORDS_JSON_PATH, "r", encoding="utf-8") as _f:
    VOCAB_SIZE: int = max(int(k) for k in json.load(_f).keys()) + 1
