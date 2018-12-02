package net.mert.reportingapi.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.mert.reportingapi.model.Customer;
import net.mert.reportingapi.model.response.transaction.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionListItem {
    private Fx fx;
    private Customer customerInfo;
    private Merchant merchant;
    private Transaction transaction;
    private Ipn ipn;
    private Acquirer acquirer;
    private Boolean refundable;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at, updated_at;
}
