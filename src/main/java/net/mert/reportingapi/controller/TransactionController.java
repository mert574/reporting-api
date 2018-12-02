package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.TransactionListRequest;
import net.mert.reportingapi.model.request.TransactionRequest;
import net.mert.reportingapi.model.request.TransactionsReportRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> getTransaction(TransactionRequest request,
                                            @RequestHeader(name = "Authorization", required = false) TokenResponse token,
                                            BindingResult result) {

        logger.debug("getTransaction: Got a new request.\n\t{}\n", request);

        if (result.hasErrors() || request.getTransactionId() == null) {
            logger.error("getTransaction: Binding error. Error(s):\n{}", result.getAllErrors());
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED")
                    .toResponseEntity();
        } else if (token == null || token.getToken().equals("null")) {
            logger.warn("getTransaction: Request does not have a proper token. {}", token);
            return new ErrorResponse("Error: Token is invalid","DECLINED")
                    .toResponseEntity();
        }

        Optional<ResponseTemplate> transaction = transactionService.getByTransactionId(request, token);
        if (transaction.isPresent()) {
            logger.debug("getTransaction: Successful response will be sent.\n\t{}\n", transaction.get());
            return transaction.get().toResponseEntity();
        }

        logger.error("getTransaction: Cannot find the requested Transaction! {}", request);
        return new ErrorResponse("Error: An unknown error occurred","DECLINED").toResponseEntity();
    }

    @PostMapping("/transaction/list")
    public ResponseEntity<?> listTransactions(@Valid TransactionListRequest request,
                                  @RequestHeader(name = "Authorization", required = false) TokenResponse token,
                                  BindingResult result) {

        logger.debug("listTransactions: Got a new request.\n\t{}\n", request);

        if (result.hasErrors()) {
            logger.error("listTransactions: Binding error. Error(s):\n{}", result.getAllErrors());
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED")
                    .toResponseEntity();
        } else if (token == null || token.getToken().equals("null")) {
            logger.warn("listTransactions: Request does not have a proper token. {}", token);
            return new ErrorResponse("Error: Token is invalid","DECLINED")
                    .toResponseEntity();
        }

        Optional<ResponseTemplate> transaction = transactionService.queryTransactions(request, token);
        if (transaction.isPresent()) {
            logger.debug("listTransactions: Successful response will be sent.\n\t{}\n",
                    transaction.get().toString());
            return transaction.get().toResponseEntity();
        }

        logger.error("getTransaction: Unknown error. Cannot execute the query.");
        return new ErrorResponse("Error: An unknown error occurred","DECLINED").toResponseEntity();
    }

    @PostMapping("/transactions/report")
    public ResponseEntity<?> transactionsReport(@Valid TransactionsReportRequest request,
            @RequestHeader(required = false, name = "Authorization") @Valid TokenResponse token,
            BindingResult result) {

        logger.debug("transactionsReport: Got a new request.\n\t{}\n", request);

        if (result.hasErrors()) {
            logger.error("transactionsReport: Binding error. Error(s):\n{}", result.getAllErrors());
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED")
                    .toResponseEntity();
        } else if (token == null || token.getToken().equals("null")) {
            logger.warn("transactionsReport: Request does not have a proper token. {}", token);
            return new ErrorResponse("Error: Token is invalid", "DECLINED")
                    .toResponseEntity();
        }

        Optional<ResponseTemplate> report = transactionService.queryReport(request, token);
        if (report.isPresent()) {
            logger.debug("transactionsReport: Successful response will be sent.\n\t{}\n",
                    report.get().toString());
            return report.get().toResponseEntity();
        }

        logger.error("transactionsReport: Unknown error. Cannot execute the query.");
        return new ErrorResponse("Error: An unknown error occurred","DECLINED").toResponseEntity();
    }
}
