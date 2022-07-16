package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private BigDecimal accountBalance;
    private int account_id;
    private  int user_id;

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }



    public Account(){};

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Account(BigDecimal accountStartingBalance){

    }

}
