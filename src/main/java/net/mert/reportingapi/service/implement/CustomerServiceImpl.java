package net.mert.reportingapi.service.implement;

import net.mert.reportingapi.model.response.CustomerInfoResponse;
import net.mert.reportingapi.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public Optional<CustomerInfoResponse> findByTransactionId(String transactionId) {
        return Optional.empty();
    }
}
