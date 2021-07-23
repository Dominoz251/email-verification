package pl.edu.wat.email.services;


import pl.edu.wat.email.dtos.UserRequestDto;
import pl.edu.wat.email.entities.ConfirmationToken;

public interface EmailService {

    void sendEmail(UserRequestDto userRequestDto, ConfirmationToken confirmationToken);
    String confirmEmail(String token);
}
