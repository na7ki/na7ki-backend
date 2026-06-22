package com.na7ki.backend.account_management.customer_inquiry;

import com.na7ki.backend.account_management.customer_inquiry.dto.request.ContactUsRequest;
import com.na7ki.backend.account_management.customer_inquiry.dto.request.ReportBugRequest;
import com.na7ki.backend.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/help-and-support")
@RequiredArgsConstructor
public class CustomerInquiryController {

    private final CustomerInquiryService customerInquiryService;





    @PostMapping("/contact-us")
    public ResponseEntity<Void> contactUs (
            @RequestBody @Valid ContactUsRequest request,
            @AuthenticationPrincipal User user
    ) {
        customerInquiryService.submitContactRequest(user, request.userMessage());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/report-bug")
    public ResponseEntity<Void> reportBug (
            @RequestBody @Valid ReportBugRequest request,
            @AuthenticationPrincipal User user
    ) {
        customerInquiryService.submitBugReport(user, request.bugDescription());
        return ResponseEntity.noContent().build();
    }

}
