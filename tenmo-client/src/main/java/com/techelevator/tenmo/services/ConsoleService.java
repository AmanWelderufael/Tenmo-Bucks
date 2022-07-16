package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import io.cucumber.java.eo.Do;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public void printBalance(Account account) {
        System.out.println("Your current balance is : " + account.getBalance());
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void printApology() {

        System.out.println("This option has not yet been implemented.");
    }

    public void printTransfer(Transfer transfer, String nameTo, String nameFrom){



        System.out.println("--------------------------------------------\n" +
                "Transfer Details\n" +
                "--------------------------------------------");

        System.out.println("ID: " + transfer.getTransfer_id());
        System.out.println("From: " + nameFrom);
        System.out.println("To: " + nameTo);
        System.out.println("Type: Send");
        System.out.println("Status: Approved");
        System.out.println("Amount: $" + transfer.getAmount());
    }
    public void  printTransfers(List<Transfer> transfers, Account account, List<User> users){

        System.out.println("-------------------------------------------\n" +
                "Transfers\n" +
                "ID          From/To                 Amount\n" +
                "-------------------------------------------");
        for (Transfer transfer: transfers){
            String sendStatus = "";

            if (account.getAccount_id()==transfer.getAccount_to()){
                sendStatus = "From: ";

            } else if(account.getAccount_id() == transfer.getAccount_from()){
                sendStatus = "To: ";
            }

            String name = "";

            for (User user : users) {

                if (transfer.getAccount_from() == user.getUser_id() + 1000) {
                    name = user.getUsername();

                }
                else if (transfer.getAccount_to() == user.getUser_id() + 1000){

                    name = user.getUsername();

                }
            }



            System.out.println(transfer.getTransfer_id() + " " + sendStatus  + name + " $ "+ transfer.getAmount());
        }
        System.out.println("Please enter transfer ID to view details");
        String transferIdSelection = scanner.nextLine();
        int transferIdPick = Integer.parseInt(transferIdSelection);



        Transfer transferPick = null;
        for (Transfer transfer: transfers){
            if (transfer.getTransfer_id()==transferIdPick){
                transferPick = transfer;
            }
        }

        String nameFrom = "";
        String nameTo = "";


        for (User user : users) {

            if (transferPick.getAccount_from() == user.getUser_id() + 1000) {
                nameFrom = user.getUsername();
                nameTo =  "Me Myselfandi";


            }
            else if (transferPick.getAccount_to() == user.getUser_id() + 1000) {
                nameFrom = "Me Myselfandi";
                nameTo = user.getUsername();


            }
        }
        printTransfer(transferPick, nameTo, nameFrom);



    }

    public void printUsers(List<User> users){

        System.out.println("-------------------------------------------\n" +
                            "Users\n" +
                             "ID          Name\n" +
                            "-------------------------------------------\n");

        for (User user : users) {
            System.out.println(user.getUser_id() + " " + user.getUsername());
        }

    }

    public Transfer promptForTransferData(int account_id, double amountBalance) {

        Transfer newTransfer = new Transfer();

            newTransfer.setTransfer_type_id(2);
            newTransfer.setAccount_from(account_id);
            newTransfer.setTransfer_status_id(2);
            String accountTo = promptForString("Enter id of user you're sending to: ");

            newTransfer.setAccount_to(Integer.parseInt(accountTo));

            String amountToSend = promptForString("Enter amount: ");
            double amountAsDouble = Double.parseDouble(amountToSend);

            if (amountAsDouble <= amountBalance && amountAsDouble > 0) {
                BigDecimal actualAmount = new BigDecimal(amountToSend);
                newTransfer.setAmount(actualAmount);
            } else {
                System.out.println("Invalid amount");
            }





        return newTransfer;
    }

    private Transfer makeTransfer(String csv) {
        Transfer transfer = null;
        String[] parsed = csv.split(",");
//        if (parsed.length == 2) {


                transfer = new Transfer();

                transfer.setTransfer_type_id(2);
                transfer.setTransfer_status_id(2);
                transfer.setAccount_to(Integer.parseInt(parsed[0].trim()));
                Double amountDouble = new Double(parsed[1].trim());
                transfer.setAmount(BigDecimal.valueOf(amountDouble));

        return transfer;
    }


    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

}
