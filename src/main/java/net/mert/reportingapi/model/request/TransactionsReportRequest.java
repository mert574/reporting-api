package net.mert.reportingapi.model.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TransactionsReportRequest {

    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private Date fromDate, toDate;
    
    private int merchant, acquirer;
}
