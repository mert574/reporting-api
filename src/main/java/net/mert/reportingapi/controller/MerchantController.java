package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.MerchantLoginRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.MerchantLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class MerchantController {

    private final MerchantLoginService merchantService;

    @Autowired
    public MerchantController(MerchantLoginService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping("/merchant/user/login")
    public ResponseEntity<?> handleLogin(@ModelAttribute("MerchantLoginRequest") @Valid MerchantLoginRequest request,
                                         BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED").toResponseEntity();
        }

        Optional<TokenResponse> token = merchantService.login(request);
        if (token.isPresent()) {
            return token.get().toResponseEntity();
        }

        return new ErrorResponse("Error: Merchant User credentials is not valid","DECLINED").toResponseEntity();
    }
}
