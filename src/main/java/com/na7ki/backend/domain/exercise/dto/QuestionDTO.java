package com.na7ki.backend.domain.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String questionText;
    private String correctAnswer;  // "SINGULAR" or "PLURAL"
    private ImageDTO image;        // set for single-image naming exercises; null otherwise
    private List<ChoiceDTO> choices;
}
