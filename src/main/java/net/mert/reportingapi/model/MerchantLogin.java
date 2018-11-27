package net.mert.reportingapi.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class MerchantLogin {

    @NotEmpty @Email
    private String email;

    @NotEmpty
    private String password;
}
