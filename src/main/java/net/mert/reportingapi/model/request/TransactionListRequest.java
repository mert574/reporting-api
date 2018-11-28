package net.mert.reportingapi.model.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import java.util.Date;

@Data
public class TransactionListRequest {

    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private Date fromDate, toDate;

    @Max(64)
    private String status, operation;

    private int merchantId, acquirerId;

    @Max(32)
    private String paymentMethod;

    @Max(256)
    private String errorCode;

    @Max(128)
    private String filterField;

    @Max(256)
    private String filterValue;

    private int page;
}
