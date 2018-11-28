package net.mert.reportingapi.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.mert.reportingapi.model.TransactionReport;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TransactionsReportResponse extends ResponseTemplate {

    private List<TransactionReport> response = new ArrayList<>();
}
