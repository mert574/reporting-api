package net.mert.reportingapi.model.response.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ipn {
    private Boolean received;
    private Boolean sent;
    private Merchant merchant;
}
