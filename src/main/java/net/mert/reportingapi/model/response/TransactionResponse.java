package net.mert.reportingapi.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.mert.reportingapi.model.response.transaction.Customer;
import net.mert.reportingapi.model.response.transaction.Fx;
import net.mert.reportingapi.model.response.transaction.Merchant;
import net.mert.reportingapi.model.response.transaction.Transaction;

@Data
@EqualsAndHashCode(callSuper=false)
public class TransactionResponse extends ResponseTemplate {
    private Fx fx;
    private Customer customerInfo;
    private Merchant merchant;
    private Transaction transaction;
}
