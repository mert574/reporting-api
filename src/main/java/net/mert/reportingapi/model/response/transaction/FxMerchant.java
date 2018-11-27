package net.mert.reportingapi.model.response.transaction;

import lombok.Data;

import javax.validation.constraints.Max;
import java.math.BigDecimal;

@Data
public class FxMerchant {

    private BigDecimal originalAmount;

    @Max(3)
    private String originalCurrency;
}
