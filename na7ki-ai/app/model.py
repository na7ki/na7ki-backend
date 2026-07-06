"""
Model architecture — copied verbatim from the notebook's Phase 3.
Must match exactly, or the saved state_dict won't load.
"""

from typing import Tuple

import torch
import torch.nn as nn
import torchvision.models as models

from app.config import EMBED_DIM


class ArabicPronunciationVerifier(nn.Module):
    def __init__(
        self,
        vocab_size: int,
        embed_dim: int = EMBED_DIM,
        frozen_layers: Tuple[str, ...] = ("layer1", "layer2"),
    ) -> None:
        super().__init__()

        resnet = models.resnet18(weights=None)  # weights come from the checkpoint, not ImageNet, at inference time

        original_conv1 = resnet.conv1
        new_conv1 = nn.Conv2d(
            in_channels=1,
            out_channels=original_conv1.out_channels,
            kernel_size=original_conv1.kernel_size,
            stride=original_conv1.stride,
            padding=original_conv1.padding,
            bias=False,
        )
        resnet.conv1 = new_conv1
        resnet.fc = nn.Identity()
        self.audio_encoder = resnet

        for name, param in self.audio_encoder.named_parameters():
            if any(name.startswith(layer) for layer in frozen_layers):
                param.requires_grad = False

        self.word_embedding = nn.Embedding(num_embeddings=vocab_size, embedding_dim=embed_dim)

        fused_dim = 512 + embed_dim
        self.classifier = nn.Sequential(
            nn.Linear(fused_dim, 128),
            nn.BatchNorm1d(128),
            nn.ReLU(inplace=True),
            nn.Dropout(0.4),
            nn.Linear(128, 1),
        )

    def forward(self, mel_spec: torch.Tensor, word_id: torch.Tensor) -> torch.Tensor:
        audio_feat = self.audio_encoder(mel_spec)
        text_feat = self.word_embedding(word_id)
        fused = torch.cat([audio_feat, text_feat], dim=1)
        logit = self.classifier(fused).squeeze(1)
        return logit
