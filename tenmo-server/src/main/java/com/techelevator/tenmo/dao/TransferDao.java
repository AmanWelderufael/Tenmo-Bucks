package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    // implement other methods later (view transfer)
    Transfer addTransfer(Transfer newTransfer);

    List<Transfer> listOfTransfers(String userAccount);
}
