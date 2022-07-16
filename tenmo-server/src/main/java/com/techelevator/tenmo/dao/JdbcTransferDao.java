package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public JdbcTransferDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Transfer> listOfTransfers(String userAccount) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer\n" +
                " JOIN account ON transfer.account_from  = account.account_id or transfer.account_to  = account.account_id\n" +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id\n" +
                " WHERE tenmo_user.username = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userAccount);
 while (result.next()) {
            Transfer transfer = mapRowFromTransfer(result);
            transfers.add(transfer);
        }
        return transfers;
    }


    @Override
    public Transfer addTransfer(Transfer newTransfer) {


        String accountSql = "SELECT account_id FROM account\n" +
                "  JOIN tenmo_user ON tenmo_user.user_id=account.user_id\n" +
                " where tenmo_user.user_id = ?";
        //This is the user_id
        int selectedDestinationUserId = newTransfer.getAccount_to();

        //This is the account_id
        Integer destinationAccountId = jdbcTemplate.queryForObject(accountSql, Integer.class,
                selectedDestinationUserId);

        String transferSql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";

        Integer transferId = jdbcTemplate.queryForObject(transferSql, Integer.class,

                newTransfer.getTransfer_type_id(),
                newTransfer.getTransfer_status_id(),
                newTransfer.getAccount_from(),
                destinationAccountId,
                newTransfer.getAmount()
        );
        newTransfer.setTransfer_id(transferId);

        String increaseBalanceSql = "update account set balance = balance + ?\n" +
                " WHERE account_id = ?;";

        String decreaseBalanceSql = " update account set balance = balance - ?\n" +
                " WHERE account_id = ?;";

        jdbcTemplate.update(increaseBalanceSql, newTransfer.getAmount(), destinationAccountId);

        jdbcTemplate.update(decreaseBalanceSql, newTransfer.getAmount(), newTransfer.getAccount_from());


        return newTransfer;
    }

    private Transfer mapRowFromTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rs.getInt("transfer_id"));
        transfer.setTransfer_type_id(rs.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rs.getInt("transfer_status_id"));
        transfer.setAccount_from(rs.getInt("account_from"));
        transfer.setAccount_to(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;

    }

}
