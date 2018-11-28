package net.mert.reportingapi.service;

import net.mert.reportingapi.model.request.TransactionsReportRequest;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;

import java.util.Optional;

public interface TransactionsService {
    Optional<ResponseTemplate> queryReport(TransactionsReportRequest request, TokenResponse token);
}
