package com.eazybank.accounts.service;

import com.eazybank.accounts.dto.CustomerDetailsDto;
import org.springframework.web.bind.annotation.RequestHeader;

public interface CustomersService {

    CustomerDetailsDto fetchCustomerDetails(String correlationId, String mobileNumber);
}
