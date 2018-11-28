package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.ClientRequest;
import net.mert.reportingapi.model.response.CustomerInfoResponse;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client")
    public ResponseEntity<?> getClient(@ModelAttribute("ClientRequest") ClientRequest request,
                                       @RequestHeader(name = "Authorization", required = false) TokenResponse token,
                                       BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED")
                    .toResponseEntity();
        } else if (token == null) {
            return new ErrorResponse("Error: Token is invalid","DECLINED")
                    .toResponseEntity();
        }

        Optional<ResponseTemplate> customerInfo = clientService.getByTransactionId(request, token);
        if (customerInfo.isPresent()) {
            return customerInfo.get().toResponseEntity();
        }

        return new ErrorResponse("Error: Customer not found","DECLINED").toResponseEntity();
    }
}
