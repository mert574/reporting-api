package net.mert.reportingapi.model.request;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Data
public class TransactionsReportRequest {

    @NotEmpty
    @Temporal(value = TemporalType.DATE)
    private String fromDate, toDate;
    
    private int merchant, acquirer;
}
