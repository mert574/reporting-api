package net.mert.reportingapi.model;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class Customer {
    private long id;

    @Temporal(TemporalType.DATE)
    private String created_at, updated_at, deleted_at;

    private String number;

    @Max(2)
    private String expiryMonth;

    @Max(4) @Min(4)
    private String expiryYear;

    private String startMonth, startYear;

    private String issueNumber;

    private String email;

    @Temporal(TemporalType.DATE)
    private String birthday;

    private String gender;

    private String billingTitle,
            billingFirstName,
            billingLastName,
            billingCompany,
            billingAddress1,
            billingAddress2,
            billingCity,
            billingPostcode,
            billingState,
            billingCountry,
            billingPhone,
            billingFax;

    private String shippingTitle,
            shippingFirstName,
            shippingLastName,
            shippingCompany,
            shippingAddress1,
            shippingAddress2,
            shippingCity,
            shippingPostcode,
            shippingState,
            shippingCountry,
            shippingPhone,
            shippingFax;
}
