package net.mert.reportingapi.controller;

import net.mert.reportingapi.model.request.MerchantLoginRequest;
import net.mert.reportingapi.model.response.ErrorResponse;
import net.mert.reportingapi.model.response.TokenResponse;
import net.mert.reportingapi.service.MerchantLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class MerchantController {

    private final MerchantLoginService merchantService;
    private Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    public MerchantController(MerchantLoginService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping("/merchant/user/login")
    public ResponseEntity<?> handleLogin(@Valid MerchantLoginRequest request,
                                         BindingResult result) {
        logger.debug("handleLogin: Got a new request. {}", request);

        if (result.hasErrors()) {
            logger.error("handleLogin: Binding error. Error(s):\n\t{}\n", result.getAllErrors());
            return new ErrorResponse("Error: Required parameters are malformed","DECLINED")
                    .toResponseEntity();
        }

        Optional<TokenResponse> token = merchantService.login(request);
        if (token.isPresent()) {
            logger.debug("handleLogin: Successful response will be sent to user. {}", token.get());
            return token.get().toResponseEntity();
        }

        logger.error("handleLogin: Credentials are not valid or an unknown error occurred. {}", request);
        return new ErrorResponse("Error: Merchant User credentials is not valid","DECLINED")
                .toResponseEntity();
    }
}
