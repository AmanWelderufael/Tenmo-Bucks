package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();
    private final UserService userService = new UserService();
    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }



    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            accountService.setAuthToken(currentUser.getToken());
            userService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
    Account account = accountService.getAccount();
    if (account != null){
        consoleService.printBalance(account);

    } else {
        consoleService.printErrorMessage();
    }

		
	}

	private void viewTransferHistory() {
        List<User> usersList = userService.listOfUsers();

    User user = new User();
        Account account = accountService.getAccount();
		// TODO Auto-generated method stub
        List<Transfer> transfers = transferService.returnListOfTransfers();
        consoleService.printTransfers(transfers,account,usersList);

		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub

        consoleService.printApology();
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        // print list of users
       List<User> users = userService.listOfUsers();

       consoleService.printUsers(users);
        // System.out.println(users);

        // pick to send money
        Account account = accountService.getAccount();
        BigDecimal amountBalanceAsBigDecimal = accountService.getAccount().getBalance();
        double amountBalance = amountBalanceAsBigDecimal.doubleValue();
        int accountId = account.getAccount_id();

    Transfer transferEnteredByUser = consoleService.promptForTransferData(accountId, amountBalance);
    Transfer transferFromAPI = transferService.addTransfer(transferEnteredByUser);


    if (transferFromAPI == null){
        consoleService.printErrorMessage();
    }

	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		consoleService.printApology();
	}

}
