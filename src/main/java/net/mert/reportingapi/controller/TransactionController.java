package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.TransactionListRequest;
import net.mert.reportingapi.model.request.TransactionRequest;
import net.mert.reportingapi.model.request.TransactionsReportRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @SuppressWarnings("Duplicates")
    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@ModelAttribute("TransactionRequest") TransactionRequest transactionRequest,
                                         @RequestHeader(name = "Authorization", required = false) TokenResponse token,
                                         BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED")
                    .toResponseEntity();
        } else if (token == null) {
            return new ErrorResponse("Error: Token is invalid","DECLINED")
                    .toResponseEntity();
        }

        Optional<ResponseTemplate> transaction = transactionService.getByTransactionId(transactionRequest, token);
        if (transaction.isPresent()) {
            return transaction.get().toResponseEntity();
        }

        return new ErrorResponse("Error: An unknown error occurred","DECLINED").toResponseEntity();
    }

    @PostMapping("/transaction/list")
    public ResponseEntity<?> list(@ModelAttribute(name = "TransactionListRequest") @Valid TransactionListRequest request,
                                             @RequestHeader(name = "Authorization", required = false) TokenResponse token,
                                             BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED")
                    .toResponseEntity();
        } else if (token == null) {
            return new ErrorResponse("Error: Token is invalid","DECLINED")
                    .toResponseEntity();
        }

        Optional<ResponseTemplate> transaction = transactionService.queryTransactions(request, token);
        if (transaction.isPresent()) {
            return transaction.get().toResponseEntity();
        }

        return new ErrorResponse("Error: An unknown error occurred","DECLINED").toResponseEntity();
    }

    @PostMapping("/transactions/report")
    public ResponseEntity<?> report(
            @ModelAttribute("TransactionsReportRequest") @Valid TransactionsReportRequest reportRequest,
            @RequestHeader(required = false, name = "Authorization") @Valid TokenResponse token,
            BindingResult result) {

        if (result.hasErrors()) {
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED")
                    .toResponseEntity();
        } else if (token == null) {
            return new ErrorResponse("Error: Token is invalid","DECLINED")
                    .toResponseEntity();
        }

        Optional<ResponseTemplate> report = transactionService.queryReport(reportRequest, token);
        if (report.isPresent()) {
            return report.get().toResponseEntity();
        }

        return new ErrorResponse("Error: An unknown error occurred","DECLINED").toResponseEntity();
    }
}
