package com.eazybank.accounts.service;

import com.eazybank.accounts.dto.CustomerDto;
import org.springframework.stereotype.Service;


public interface AccountsService {

     void createAccount(CustomerDto customerDto);

     CustomerDto fetchAccount(String mobileNumber);

     boolean updateAccount(CustomerDto customerDto);

     boolean deleteAccount(String mobileNumber);
}
