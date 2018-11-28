package net.mert.reportingapi.service;

import net.mert.reportingapi.model.request.ClientRequest;
import net.mert.reportingapi.model.response.ResponseTemplate;
import net.mert.reportingapi.model.response.TokenResponse;

import java.util.Optional;

public interface ClientService {

    Optional<ResponseTemplate> getByTransactionId(ClientRequest request, TokenResponse token);
}
