from pydantic import BaseModel


class VerifyResponse(BaseModel):
    word_id: int
    probability: float
    is_correct: bool
    threshold_used: float
    model_version: str


class HealthResponse(BaseModel):
    status: str
    model_loaded: bool
