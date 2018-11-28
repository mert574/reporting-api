package net.mert.reportingapi.service.implement;

import net.mert.reportingapi.model.request.ClientRequest;
import net.mert.reportingapi.model.response.CustomerInfoResponse;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.ClientService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<ResponseTemplate> getByTransactionId(ClientRequest request, TokenResponse token) {
        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.set("Authorization", token.getToken());

        try {
            ResponseEntity<CustomerInfoResponse> response = restTemplate.postForEntity(
                    "https://sandbox-reporting.rpdpymnt.com/api/v3/client",
                    new HttpEntity<>(request, tokenHeader),
                    CustomerInfoResponse.class);

            return Optional.ofNullable(response.getBody());
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return Optional.of(new ErrorResponse("Token Expired", "DECLINED"));
            }
        } catch (Exception exception) {}

        return Optional.empty();
    }
}
