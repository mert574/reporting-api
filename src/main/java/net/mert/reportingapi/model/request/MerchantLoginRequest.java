package net.mert.reportingapi.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class MerchantLoginRequest {

    @NotEmpty @Email
    private String email;

    @NotEmpty
    private String password;
}
