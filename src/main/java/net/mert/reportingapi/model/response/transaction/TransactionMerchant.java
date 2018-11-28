package net.mert.reportingapi.model.response.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionMerchant {

    private String referenceNo;
    private Long merchantId;

    private String status,
            channel,
            customData,
            chainId;

    private Integer agentInfoId;
    private String operation;
    private Integer fxTransactionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at, updated_at;

    private Long id;
    private Integer acquirerTransactionId;

    private String code,
            message,
            transactionId;

    private TransactionMerchantAgent agent;
}
