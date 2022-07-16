package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class TransferController {

//    @Autowired
//    private TransferDao dao;
    @Autowired
    private JdbcTransferDao dao;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer addTransfer(@RequestBody Transfer newTransfer) {

        return dao.addTransfer(newTransfer);

    }
    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getTransfers(Principal principal) {
        String user = principal.getName();
        return dao.listOfTransfers(user);
    }

}

