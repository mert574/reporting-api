package net.mert.reportingapi.service.implement;

import jdk.nashorn.internal.parser.Token;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.MerchantLoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class MerchantLoginServiceImpl implements MerchantLoginService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<TokenResponse> login(String email, String password) {

        MultiValueMap<String, String> parts = new LinkedMultiValueMap<>(2);
        parts.add("email", email);
        parts.add("password", password);

        try {
            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                    "https://sandbox-reporting.rpdpymnt.com/api/v3/merchant/user/login", parts, TokenResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return Optional.of(response.getBody());
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        return Optional.empty();
    }
}
