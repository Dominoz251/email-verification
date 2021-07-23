package pl.edu.wat.email.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.edu.wat.email.dtos.UserRequestDto;
import pl.edu.wat.email.entities.ConfirmationToken;
import pl.edu.wat.email.entities.User;
import pl.edu.wat.email.repositories.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;

    @Override
    public void sendEmail(UserRequestDto userRequestDto, ConfirmationToken confirmationToken) {
        String text = "To confirm your mail click to link below:\n";
        String title = "Confirmation email";
        try {
            String url = "http://localhost:8081/api/confirmation?token=";
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(text
                    +url+confirmationToken.getToken());
            helper.setTo(userRequestDto.getEmail());
            helper.setSubject(title);
            helper.setFrom("aplikacjatreningowa@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }
    }


    @Transactional
    @Override
    public String confirmEmail(String token)
    {
        System.out.println("Confirmation token: "+token);
        User user;
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).
                orElseThrow(()->new IllegalStateException("Token not found"));

        if(confirmationToken.getConfirmedDateTime() !=null)
        {
            return "Your email is already confirmed";
        }
        if(confirmationToken.getExpiredDateTime().isBefore(LocalDateTime.now()))
            return "Token is up to date";

        confirmationTokenService.setConfirmed(confirmationToken, token);
        user = userRepository.findByEmail(confirmationToken.getUser().getEmail());
        user.setActive(true);
        userRepository.save(user);

        return "Your email has been confirmed";
    }
}
