package net.mert.reportingapi.service.implement;

import net.mert.reportingapi.model.request.TransactionListRequest;
import net.mert.reportingapi.model.request.TransactionRequest;
import net.mert.reportingapi.model.request.TransactionsReportRequest;
import net.mert.reportingapi.model.response.*;
import net.mert.reportingapi.service.TransactionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<ResponseTemplate> getByTransactionId(TransactionRequest request, TokenResponse token) {
        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.set("Authorization", token.getToken());

        try {
            ResponseEntity<TransactionResponse> response = restTemplate.postForEntity(
                    "https://sandbox-reporting.rpdpymnt.com/api/v3/transaction",
                    new HttpEntity<>(request, tokenHeader),
                    TransactionResponse.class);

            return Optional.ofNullable(response.getBody());
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return Optional.of(new ErrorResponse("Token Expired", "DECLINED"));
            }
        } catch (Exception exception) {}

        return Optional.empty();
    }

    @Override
    public Optional<ResponseTemplate> queryTransactions(TransactionListRequest request, TokenResponse token) {
        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.set("Authorization", token.getToken());

        try {
            ResponseEntity<TransactionListResponse> response = restTemplate.postForEntity(
                    "https://sandbox-reporting.rpdpymnt.com/api/v3/transaction/list",
                    new HttpEntity<>(request, tokenHeader),
                    TransactionListResponse.class);

            return Optional.ofNullable(response.getBody());
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return Optional.of(new ErrorResponse("Token Expired", "DECLINED"));
            }
        } catch (Exception exception) {}

        return Optional.empty();
    }

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
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return Optional.of(new ErrorResponse("Token Expired", "DECLINED"));
            }
        } catch (Exception exception) {}

        return Optional.empty();
    }
}
