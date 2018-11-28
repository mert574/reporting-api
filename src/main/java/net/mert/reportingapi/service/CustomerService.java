package net.mert.reportingapi.service;

import net.mert.reportingapi.model.response.CustomerInfoResponse;

import java.util.Optional;

public interface CustomerService {

    Optional<CustomerInfoResponse> findByTransactionId(String transactionId);
}
