package net.mert.reportingapi.service;

import net.mert.reportingapi.model.request.MerchantLoginRequest;
import net.mert.reportingapi.model.response.TokenResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantLoginServiceImpl implements MerchantLoginService {

    @Override
    public Optional<TokenResponse> login(String email, String password) {

        // TODO: Consume the API somehow and get the token.
        if (email.equals(password)) {
            TokenResponse token = new TokenResponse("this-is-a-legit-token");
            return Optional.of(token);
        }

        return Optional.empty();
    }
}