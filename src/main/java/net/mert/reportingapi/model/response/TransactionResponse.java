package net.mert.reportingapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.mert.reportingapi.model.Customer;
import net.mert.reportingapi.model.response.transaction.Fx;
import net.mert.reportingapi.model.response.transaction.Merchant;
import net.mert.reportingapi.model.response.transaction.Transaction;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse extends ResponseTemplate {
    private Fx fx;
    private Customer customerInfo;
    private Merchant merchant;
    private Transaction transaction;
}
