package com.eazybank.accounts.service.client;

import com.eazybank.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;

public class CardsFallBack implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
