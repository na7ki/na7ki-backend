# na7ki-ai

FastAPI microservice serving the Arabic child speech pronunciation verifier
trained in the companion Colab notebook. Wraps `ArabicPronunciationVerifier`
(ResNet18 mel-spectrogram branch + word-id embedding branch) behind a
`/verify` endpoint.

## Setup

```bash
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

## Run

```bash
uvicorn app.main:app --reload --port 8000
```

Must be run from this directory (`na7ki-ai/`) so the relative paths in
`app/config.py` (`artifacts/verifier_checkpoint.pth`, `artifacts/words_id.json`)
resolve correctly.

## Endpoints

- `GET /health` — reports whether the model checkpoint loaded successfully.
- `POST /verify` — multipart form with `audio` (file) and `word_id` (int),
  returns a calibrated correctness probability.

## Artifacts

`artifacts/verifier_checkpoint.pth` and `artifacts/words_id.json` must stay in
sync with whatever the notebook last trained/exported — `VOCAB_SIZE` in
`app/config.py` is derived from `words_id.json` at import time, so it can't
drift from the checkpoint's embedding table on its own, but the checkpoint
itself must still be the one that was calibrated together with that file.
