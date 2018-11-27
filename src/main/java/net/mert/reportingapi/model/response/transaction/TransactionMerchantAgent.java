package net.mert.reportingapi.model.response.transaction;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Data
public class TransactionMerchantAgent {

    private long id;
    private String customerIp, customerUserAgent;
    private String merchantIp, merchantUserAgent;

    @Temporal(TemporalType.DATE)
    private String created_at, updated_at, deleted_at;
}
