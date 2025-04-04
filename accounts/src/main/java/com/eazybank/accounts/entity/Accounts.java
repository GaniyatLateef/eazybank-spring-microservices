package com.eazybank.accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "accounts")
public class Accounts extends BaseEntity {


    private Long customerId;

    @Id
    private Long accountNumber;

    private String accountType;

    private String branchAddress;

    private Boolean communicationSw;
}
