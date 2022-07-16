package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.security.Principal;

@RestController
//@PreAuthorize("isAuthenticated()")
public class AccountController {
        @Autowired
    private UserAccountDao userAccount;


    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public Account checkUserBalance(Principal principal){
       String user = principal.getName();
        return userAccount.getAccount(user);


    }
}
