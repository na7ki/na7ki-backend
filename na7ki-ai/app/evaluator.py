import os
import jellyfish
from app.normalizer import normalize_arabic

SUCCESS_THRESHOLD = float(os.getenv("SIMILARITY_SUCCESS", 0.85))
PARTIAL_THRESHOLD = float(os.getenv("SIMILARITY_PARTIAL", 0.60))

def evaluate_result(transcription: str, target: str) -> dict:
    norm_transcription = normalize_arabic(transcription)
    norm_target = normalize_arabic(target)

    similarity = jellyfish.jaro_winkler_similarity(norm_transcription, norm_target)

    if similarity >= SUCCESS_THRESHOLD:
        tier = "success"
    elif similarity >= PARTIAL_THRESHOLD:
        tier = "partial"
    else:
        tier = "fail"

    return {
        "tier": tier,
        "similarity": round(similarity, 4),
        "norm_transcription": norm_transcription,
        "norm_target": norm_target,
    }

def find_wrong_phonemes(time_stamps, target: str) -> list[str]:
    """
    Compare character-level timestamps from ForcedAligner
    against expected target to find mismatched characters.
    """
    if not time_stamps:
        return []

    wrong = []
    transcribed_chars = [t.text for t in time_stamps]
    target_chars = list(normalize_arabic(target))

    for i, expected_char in enumerate(target_chars):
        if i < len(transcribed_chars):
            if transcribed_chars[i] != expected_char:
                wrong.append({
                    "position": i,
                    "expected": expected_char,
                    "heard": transcribed_chars[i],
                })
        else:
            wrong.append({
                "position": i,
                "expected": expected_char,
                "heard": None,  # child didn't say this character at all
            })

    return wrong