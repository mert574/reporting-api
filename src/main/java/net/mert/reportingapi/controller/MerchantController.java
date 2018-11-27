package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.MerchantLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MerchantController {

    @PostMapping("/merchant/user/login")
    public ResponseEntity<?> handleLogin(MerchantLogin merch, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Hello World !", HttpStatus.OK);
    }
}
