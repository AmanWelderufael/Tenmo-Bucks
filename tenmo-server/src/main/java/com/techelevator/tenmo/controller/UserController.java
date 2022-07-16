package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {


    @Autowired
    private JdbcUserDao dao;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers(Principal principal) {
        String user = principal.getName();
        return dao.retrieveUsers(user);
    }
}
