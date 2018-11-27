package net.mert.reportingapi.service.implement;

import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.MerchantLoginService;
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
