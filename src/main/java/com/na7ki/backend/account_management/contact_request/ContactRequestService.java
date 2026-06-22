package com.na7ki.backend.account_management.contact_request;

import com.na7ki.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactRequestService {

    private final ContactRequestRepository contactRequestRepository;





    public void createContactRequest(String message, User associatedUser) {
        ContactRequest contactRequest = ContactRequest.builder()
                .message(message)
                .requester(associatedUser)
                .build();

        contactRequestRepository.save(contactRequest);
    }

}
