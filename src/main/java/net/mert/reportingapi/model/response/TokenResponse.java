package net.mert.reportingapi.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokenResponse extends ResponseTemplate {

    @NotEmpty
    private String token;
}
