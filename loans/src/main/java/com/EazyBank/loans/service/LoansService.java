package com.EazyBank.loans.service;

import com.EazyBank.loans.dto.LoansDto;

public interface LoansService {

    void createLoan(String mobileNumber);
    LoansDto fetchLoan(String mobileNumber);
    boolean updateLoan(LoansDto loansDto);
    boolean deleteLoan(String mobileNumber);
}
