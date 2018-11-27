package net.mert.reportingapi.service;

import net.mert.reportingapi.model.response.TransactionsReportResponse;

import java.util.Optional;

public interface TransactionsService {
    Optional<TransactionsReportResponse> queryReport(String fromDate, String toDate, int merchant, int acquirer);
}
