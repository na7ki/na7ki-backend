package com.na7ki.backend.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerCheckDTO {
    private Long questionId;
    private Long choiceId;
}
