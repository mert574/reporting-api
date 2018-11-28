package net.mert.reportingapi.service.implement;

import net.mert.reportingapi.model.TransactionReport;
import net.mert.reportingapi.model.response.TransactionsReportResponse;
import net.mert.reportingapi.service.TransactionsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Override
    public Optional<TransactionsReportResponse> queryReport(String fromDate, String toDate,
                                                            int merchant, int acquirer) {
        if (fromDate.isEmpty() && toDate.isEmpty()) {
            return Optional.empty();
        }

        // TODO: Consume the API somehow and get the results.
        TransactionsReportResponse resp = new TransactionsReportResponse();

        // Create mock data...
        List<TransactionReport> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TransactionReport newTransactionReport = new TransactionReport(i * 100, i * 534, i % 3 == 0 ? "USD" : "EUR");
            list.add(newTransactionReport);
        }

        resp.setTransactionReports(list);

        return Optional.of(resp);
    }
}