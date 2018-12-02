package net.mert.reportingapi.service.implement;

import net.mert.reportingapi.model.request.MerchantLoginRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.MerchantLoginService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class MerchantLoginServiceImpl implements MerchantLoginService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<ResponseTemplate> login(MerchantLoginRequest request) {

        try {
            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                    "https://sandbox-reporting.rpdpymnt.com/api/v3/merchant/user/login",
                    new HttpEntity<>(request),
                    TokenResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return Optional.of(response.getBody());
            }
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return Optional.of(new ErrorResponse("Token Expired", "DECLINED"));
            }
        }

        return Optional.empty();
    }

}
