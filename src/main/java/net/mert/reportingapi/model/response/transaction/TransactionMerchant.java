package net.mert.reportingapi.model.response.transaction;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    @Temporal(TemporalType.DATE)
    private String created_at, updated_at;

    private long id;
    private int acquirerTransactionId;

    private  String code,
            message,
            transactionId;

    private TransactionMerchantAgent agent;
}
