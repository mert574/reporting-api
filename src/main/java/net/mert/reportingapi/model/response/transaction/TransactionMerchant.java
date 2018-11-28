package net.mert.reportingapi.model.response.transaction;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TransactionMerchant {
    private String referenceNo;
    private long merchantId;

    private String status,
            channel,
            customData,
            chainId;

    private int agentInfoId;
    private String operation;
    private int fxTransactionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at, updated_at;

    private long id;
    private int acquirerTransactionId;

    private  String code,
            message,
            transactionId;

    private TransactionMerchantAgent agent;
}
