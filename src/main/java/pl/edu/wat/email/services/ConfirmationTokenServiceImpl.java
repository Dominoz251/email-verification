package pl.edu.wat.email.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wat.email.entities.ConfirmationToken;
import pl.edu.wat.email.repositories.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public void setConfirmed(ConfirmationToken confirmationToken, String token) {
        confirmationToken.setConfirmedDateTime(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);
    }
}
