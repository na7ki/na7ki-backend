package com.na7ki.backend.account_management.customer_inquiry;

import com.na7ki.backend.account_management.customer_inquiry.entity.BugReport;
import com.na7ki.backend.account_management.customer_inquiry.entity.ContactRequest;
import com.na7ki.backend.account_management.customer_inquiry.entity.CustomerInquiry;
import com.na7ki.backend.account_management.customer_inquiry.repository.BugReportRepository;
import com.na7ki.backend.account_management.customer_inquiry.repository.ContactRequestRepository;
import com.na7ki.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerInquiryService {

    private final ContactRequestRepository contactRequestRepository;
    private final BugReportRepository bugReportRepository;





    public void submitContactRequest(User requester, String userMessage) {
        ContactRequest request = createInquiry(ContactRequest.builder(), requester, userMessage);
        contactRequestRepository.save(request);
    }

    public void submitBugReport(User reporter, String bugDescription) {
        BugReport bug = createInquiry(BugReport.builder(), reporter, bugDescription);
        bugReportRepository.save(bug);
    }





    private <T extends CustomerInquiry, B extends CustomerInquiry.CustomerInquiryBuilder<T, B>> T createInquiry(
            CustomerInquiry.CustomerInquiryBuilder<T, B> builder,
            User inquirer,
            String inquiryContent
    ) {
        if (inquiryContent == null || inquiryContent.isBlank()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        return builder
                .inquiryContent(inquiryContent)
                .inquirer(inquirer)
                .build();
    }

}
