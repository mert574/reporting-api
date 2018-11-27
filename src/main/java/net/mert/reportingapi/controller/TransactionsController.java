package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.TransactionsReportRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.TransactionsReportResponse;
import net.mert.reportingapi.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
            BindingResult result) {

        if (result.hasErrors()) {
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED").toResponseEntity();
        }

        Optional<TransactionsReportResponse> report = transactions.queryReport( transRequest.getFromDate(),
                                                                                transRequest.getToDate(),
                                                                                transRequest.getMerchant(),
                                                                                transRequest.getAcquirer());

        if (report.isPresent()) {
            return report.get().toResponseEntity();
        }

        return new ErrorResponse("Error: Required parameters are malformed","DECLINED").toResponseEntity();
    }
}
