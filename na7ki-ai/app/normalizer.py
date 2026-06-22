import re

def normalize_arabic(text: str) -> str:
    if not text:
        return ""
    # Remove diacritics (tashkeel)
    text = re.sub(r'[\u0617-\u061A\u064B-\u0652]', '', text)
    # Normalize alef forms → ا
    text = re.sub(r'[إأآا]', 'ا', text)
    # Normalize teh marbuta → ه
    text = re.sub(r'ة', 'ه', text)
    # Normalize yeh forms → ي
    text = re.sub(r'ى', 'ي', text)
    # Remove tatweel
    text = re.sub(r'ـ', '', text)
    # Remove extra whitespace
    text = re.sub(r'\s+', ' ', text).strip()
    return text