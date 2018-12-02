package net.mert.reportingapi.model.response.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import java.math.BigInteger;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Merchant {

    private String name;
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

    private BigInteger amount,
            originalAmount,
            convertedAmount;

    @Max(3)
    private String currency,
            originalCurrency,
            convertedCurrency;

    private String type,
            paymentType,
            token,
            ipnType;

//    TODO: IPNUrl && Date as timestamp
    @JsonProperty("IPNUrl")
    private String ipnUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Boolean allowPartialCapture,
            allowPartialRefund;

    private Agent agent;
}
