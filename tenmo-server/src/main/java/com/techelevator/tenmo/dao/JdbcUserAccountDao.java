package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserAccountDao implements UserAccountDao {
    private int account_id;
    private int user_id;
    private BigDecimal accountBalance;
    private JdbcTemplate jdbcTemplate;

    public JdbcUserAccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //This code returns the account balance of a user
    @Override
    public Account getAccount(String user){
        Account account = null;
        String sql = "SELECT * FROM account\n" +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id \n" +
                "WHERE tenmo_user.username = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,user);
        if (result.next()){
            account = mapRowToAccount(result);
        } if (account == null) {
            System.out.println("Account not found");
        }
        return account;


    }



    private Account mapRowToAccount (SqlRowSet results){
        Account account = new Account();
        account.setAccount_id(results.getInt("account_id"));
        account.setUser_id(results.getInt("user_id"));
        account.setAccountBalance(results.getBigDecimal("balance"));

        return account;
    }



}
