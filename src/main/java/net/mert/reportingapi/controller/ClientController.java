package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.ClientRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ClientController {

    private final ClientService clientService;
    private Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client")
    public ResponseEntity<?> getClient(ClientRequest request,
                                       @RequestHeader(name = "Authorization", required = false) TokenResponse token,
                                       BindingResult result) {

        logger.debug("getClient: Got a new request. {}", request);

        if (result.hasErrors() || request.getTransactionId() == null) {
            logger.error("getClient: Binding error. Error(s):\n\t{}\n", result.getAllErrors());
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED")
                    .toResponseEntity();
        } else if (token == null || token.getToken().equals("null")) {
            logger.warn("getClient: Request does not have a proper token. {}", token);
            return new ErrorResponse("Error: Token is invalid","DECLINED").toResponseEntity();
        }

        Optional<ResponseTemplate> customerInfo = clientService.getByTransactionId(request, token);
        if (customerInfo.isPresent()) {
            logger.debug("getClient: Successful response will be sent.\n\t{}\n", customerInfo.get());
            return customerInfo.get().toResponseEntity();
        }

        logger.error("getClient: Cannot find the requested Customer! {}", request);
        return new ErrorResponse("Error: Customer not found","DECLINED").toResponseEntity();
    }
}
