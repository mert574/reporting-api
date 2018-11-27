package net.mert.reportingapi.service;

import net.mert.reportingapi.model.response.TransactionResponse;

import java.util.Optional;

public interface TransactionService {
    Optional<TransactionResponse> getByTransactionId(String transactionId);
}
