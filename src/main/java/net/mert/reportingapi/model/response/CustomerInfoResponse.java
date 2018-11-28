package net.mert.reportingapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.mert.reportingapi.model.Customer;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoResponse extends ResponseTemplate {
    private Customer customerInfo;
}
