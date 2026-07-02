package com.na7ki.backend.domain.exercise.Service;

import com.na7ki.backend.domain.exercise.Entity.Cases;
import com.na7ki.backend.domain.exercise.Repository.CasesRepository;
import com.na7ki.backend.domain.exercise.dto.CaseRequest;
import com.na7ki.backend.domain.exercise.dto.CaseResponse;
import com.na7ki.backend.domain.exercise.exception.CaseNotFoundException;
import com.na7ki.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CasesService {

    private final CasesRepository casesRepository;

    public CaseResponse createCase(User specialist, CaseRequest request) {
        Cases newCase = new Cases();
        newCase.setUserId(specialist.getUserId());
        newCase.setChildName(request.getChildName());
        Cases saved = casesRepository.save(newCase);
        return toResponse(saved);
    }

    public CaseResponse getCase(Long caseId) {
        Cases found = casesRepository.findById(caseId)
                .orElseThrow(() -> new CaseNotFoundException(caseId));
        return toResponse(found);
    }

    public List<CaseResponse> getMyCases(User specialist) {
        return casesRepository.findByUserId(specialist.getUserId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteCase(User specialist, Long caseId) {
        Cases found = casesRepository.findById(caseId)
                .orElseThrow(() -> new CaseNotFoundException(caseId));
        if (!found.getUserId().equals(specialist.getUserId())) {
            throw new SecurityException("You are not authorized to delete this case");
        }
        casesRepository.delete(found);
    }

    private CaseResponse toResponse(Cases cases) {
        return CaseResponse.builder()
                .id(cases.getId())
                .userId(cases.getUserId())
                .childName(cases.getChildName())
                .build();
    }
}
