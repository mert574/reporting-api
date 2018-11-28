package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.ClientRequest;
import net.mert.reportingapi.model.Customer;
import net.mert.reportingapi.model.response.CustomerInfoResponse;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ClientController {

    private final CustomerService customerService;

    @Autowired
    public ClientController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/client")
    public ResponseEntity<?> getClient(@ModelAttribute("ClientRequest") ClientRequest client, BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED").toResponseEntity();
        }

        Optional<CustomerInfoResponse> customerInfo = customerService.findByTransactionId(client.getTransactionId());
        if (customerInfo.isPresent()) {
            return customerInfo.get().toResponseEntity();
        }

        return new ErrorResponse("Error: Customer not found","DECLINED").toResponseEntity();
    }
}
