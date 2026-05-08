package com.na7ki.backend.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitSessionDTO {
    private Long packageId;
    private Long userId;
    private List<AnswerSubmissionDTO> answers;
}
