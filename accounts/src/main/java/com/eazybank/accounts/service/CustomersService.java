package com.eazybank.accounts.service;

import com.eazybank.accounts.dto.CustomerDetailsDto;

public interface CustomersService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
