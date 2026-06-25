import torch
import os
from qwen_asr import Qwen3ASRModel

_model = None

def get_model():
    global _model
    if _model is not None:
        return _model

    use_vllm = os.getenv("USE_VLLM", "false").lower() == "true"

    if use_vllm:
        # Faster — use if you have a good GPU
        _model = Qwen3ASRModel.LLM(
            model="Qwen/Qwen3-ASR-1.7B",
            gpu_memory_utilization=0.7,
            max_new_tokens=256,
            forced_aligner="Qwen/Qwen3-ForcedAligner-0.6B",
            forced_aligner_kwargs=dict(
                dtype=torch.bfloat16,
                device_map="cuda:0",
            ),
        )
    else:
        # Simpler — works on CPU too (slower)
        _model = Qwen3ASRModel.from_pretrained(
            "Qwen/Qwen3-ASR-1.7B",
            dtype=torch.bfloat16 if torch.cuda.is_available() else torch.float32,
            device_map="cuda:0" if torch.cuda.is_available() else "cpu",
            max_new_tokens=256,
            forced_aligner="Qwen/Qwen3-ForcedAligner-0.6B",
            forced_aligner_kwargs=dict(
                dtype=torch.bfloat16 if torch.cuda.is_available() else torch.float32,
                device_map="cuda:0" if torch.cuda.is_available() else "cpu",
            ),
        )

    return _model