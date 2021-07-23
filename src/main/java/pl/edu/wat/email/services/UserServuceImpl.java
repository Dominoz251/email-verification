package pl.edu.wat.email.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wat.email.dtos.UserRequestDto;
import pl.edu.wat.email.entities.ConfirmationToken;
import pl.edu.wat.email.entities.User;
import pl.edu.wat.email.repositories.ConfirmationTokenRepository;
import pl.edu.wat.email.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServuceImpl implements UserService{

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void addUser(UserRequestDto userRequestDto) {
        User userDB = new User();
        userDB.setName(userRequestDto.getName());
        userDB.setSurname(userRequestDto.getSurname());
        userDB.setEmail(userRequestDto.getEmail());
        userRepository.save(userDB);

        userDB.setActive(false);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now().plusMinutes(30),
                LocalDateTime.now(), userDB);

        confirmationTokenRepository.save(confirmationToken);

        emailService.sendEmail(userRequestDto, confirmationToken);
    }
}
