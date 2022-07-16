package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Account {
    @JsonProperty("account_id")
    private int account_id;
    @JsonProperty("user_id")
    private int user_id;
    @JsonProperty("accountBalance")
    private BigDecimal accountBalance;


    public Account(){

    }


    public int getAccount_id() {
        return account_id;
    }

    public int getUser_Id() {
        return user_id;
    }

    public BigDecimal getBalance() {
        return accountBalance;
    }

    public void setBalance(BigDecimal balance) {
        this.accountBalance = balance;
    }

//    @Override
//    public String toString() {
//
//        return "\n   ------------ " +
//                "\n accountBalance: " + accountBalance;
//
//    }
}
