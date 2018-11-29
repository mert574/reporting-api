package net.mert.reportingapi.service;

import net.mert.reportingapi.model.request.TransactionListRequest;
import net.mert.reportingapi.model.request.TransactionRequest;
import net.mert.reportingapi.model.request.TransactionsReportRequest;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;

import java.util.Optional;

public interface TransactionService {
    Optional<ResponseTemplate> getByTransactionId(TransactionRequest request, TokenResponse token);
    Optional<ResponseTemplate> queryTransactions(TransactionListRequest request, TokenResponse token);
    Optional<ResponseTemplate> queryReport(TransactionsReportRequest request, TokenResponse token);
}
