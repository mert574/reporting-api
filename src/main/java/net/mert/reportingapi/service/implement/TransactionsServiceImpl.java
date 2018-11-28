package net.mert.reportingapi.service.implement;

import net.mert.reportingapi.model.request.TransactionsReportRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.model.response.TransactionsReportResponse;
import net.mert.reportingapi.service.TransactionsService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<ResponseTemplate> queryReport(TransactionsReportRequest request, TokenResponse token) {
        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.set("Authorization", token.getToken());

        try {
            ResponseEntity<TransactionsReportResponse> response = restTemplate.postForEntity(
                    "https://sandbox-reporting.rpdpymnt.com/api/v3/transactions/report",
                    new HttpEntity<>(request, tokenHeader),
                    TransactionsReportResponse.class);

            return Optional.ofNullable(response.getBody());

        } catch (Exception exception) {
            return Optional.of(new ErrorResponse(exception.getMessage(), "DECLINED"));
        }
    }
}
