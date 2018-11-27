package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.MerchantLoginRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MerchantController {

    @PostMapping("/merchant/user/login")
    public ResponseEntity<?> handleLogin(@ModelAttribute("MerchantLoginRequest") @Valid MerchantLoginRequest merch, BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorResponse("bad request", "DECLINED").toResponseEntity();
        }

        // Consume the API somehow and get the token.
        TokenResponse token = new TokenResponse();
        token.setToken("this-is-a-legit-token");

        return token.toResponseEntity();
    }
}
