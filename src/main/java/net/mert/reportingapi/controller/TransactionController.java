package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.TransactionRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.TransactionResponse;
import net.mert.reportingapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@ModelAttribute("TransactionRequest") TransactionRequest transactionRequest,
                                         BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED").toResponseEntity();
        }

        Optional<TransactionResponse> transaction = transactionService.getByTransactionId(transactionRequest.getTransactionId());
        if (transaction.isPresent()) {
            return new TransactionResponse().toResponseEntity();
        }

        return new ErrorResponse("Error: An unknown error occurred","DECLINED").toResponseEntity();
    }
}
