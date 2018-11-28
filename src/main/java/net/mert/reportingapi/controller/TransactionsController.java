package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.TransactionsReportRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.TransactionsService;
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
public class TransactionsController {

    private final TransactionsService transactions;

    @Autowired
    public TransactionsController(TransactionsService transactions) {
        this.transactions = transactions;
    }

    @PostMapping("/transactions/report")
    public ResponseEntity<?> report(
            @ModelAttribute("TransactionsReportRequest") @Valid TransactionsReportRequest transRequest,
            @RequestHeader(required = false, name = "Authorization") @Valid TokenResponse token,
            BindingResult result) {

        if (result.hasErrors()) {
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED")
                    .toResponseEntity();
        } else if (token == null) {
            return new ErrorResponse("Error: Token is invalid","DECLINED")
                    .toResponseEntity();
        }

        Optional<ResponseTemplate> report = transactions.queryReport(transRequest, token);
        if (report.isPresent()) {
            return report.get().toResponseEntity();
        }

        return new ErrorResponse("Error: An unknown error occurred","DECLINED").toResponseEntity();
    }
}
