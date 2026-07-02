package com.na7ki.backend.domain.exercise.controller;

import com.na7ki.backend.domain.exercise.Service.CasesService;
import com.na7ki.backend.domain.exercise.dto.CaseRequest;
import com.na7ki.backend.domain.exercise.dto.CaseResponse;
import com.na7ki.backend.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
@RequiredArgsConstructor
public class CasesController {

    private final CasesService casesService;

    // POST /api/cases — specialist creates a new case
    @PostMapping
    public ResponseEntity<CaseResponse> createCase(
            @AuthenticationPrincipal User specialist,
            @Valid @RequestBody CaseRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(casesService.createCase(specialist, request));
    }

    // GET /api/cases/{id} — get a specific case by ID
    @GetMapping("/{id}")
    public ResponseEntity<CaseResponse> getCase(@PathVariable Long id) {
        return ResponseEntity.ok(casesService.getCase(id));
    }

    // GET /api/cases/my-cases — get all cases belonging to the logged-in specialist
    @GetMapping("/my-cases")
    public ResponseEntity<List<CaseResponse>> getMyCases(
            @AuthenticationPrincipal User specialist
    ) {
        return ResponseEntity.ok(casesService.getMyCases(specialist));
    }

    // DELETE /api/cases/{id} — delete a case (only the owning specialist can delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCase(
            @AuthenticationPrincipal User specialist,
            @PathVariable Long id
    ) {
        casesService.deleteCase(specialist, id);
        return ResponseEntity.noContent().build();
    }
}
