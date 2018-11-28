package net.mert.reportingapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {
    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at, updated_at, deleted_at;

    private String number;

    @Max(2)
    private String expiryMonth;

    @Max(4) @Min(4)
    private String expiryYear;

    private String startMonth, startYear;

    private String issueNumber;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

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
