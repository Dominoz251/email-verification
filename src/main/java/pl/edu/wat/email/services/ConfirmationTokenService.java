package pl.edu.wat.email.services;

import pl.edu.wat.email.entities.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {

    Optional<ConfirmationToken> getToken(String token);

    void setConfirmed(ConfirmationToken confirmationTokenm, String token);
}
