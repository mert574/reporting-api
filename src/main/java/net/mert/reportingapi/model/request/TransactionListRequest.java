package net.mert.reportingapi.model.request;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;

@Data
public class TransactionListRequest {

    @Temporal(TemporalType.DATE)
    private String fromDate, toDate;

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
