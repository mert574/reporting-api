package net.mert.reportingapi.service.implement;

import net.mert.reportingapi.model.response.TransactionResponse;
import net.mert.reportingapi.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public Optional<TransactionResponse> getByTransactionId(String transactionId) {
        // TODO: Consume the API somehow and get the transaction.
        return Optional.empty();
    }
}
