package net.mert.reportingapi.service;

import net.mert.reportingapi.model.request.MerchantLoginRequest;
import net.mert.reportingapi.model.response.TokenResponse;

import java.util.Optional;

public interface MerchantLoginService {

    Optional<TokenResponse> login(MerchantLoginRequest request);
}
