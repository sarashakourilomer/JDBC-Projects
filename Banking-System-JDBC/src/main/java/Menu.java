import java.math.BigDecimal;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private final CustomerDAO customerDAO;
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
    private final BankService bankService;

    public Menu() {
        scanner = new Scanner(System.in);
        customerDAO = new CustomerDAO();
        accountDAO = new AccountDAO();
        transactionDAO = new TransactionDAO();
        bankService = new BankService();
    }

    public void start() {

        int choice;

        do {
            showMenu();

            System.out.print("Please select your choice: ");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    openAccount();
                    break;

                case 2:
                    deposit();
                    break;

                case 3:
                    withdraw();
                    break;

                case 4:
                    transfer();
                    break;

                case 5:
                    findCustomer();
                    break;

                case 6:
                    addCustomer();
                    break;

                case 7:
                    updateCustomer();
                    break;

                case 8:
                    deleteCustomer();
                    break;

                case 9:
                    findAccount();
                    break;

                case 10:
                    showTransactions();
                    break;

                case 11:
                    System.out.println("Good Bye!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 11);
    }

    private void showMenu() {

        System.out.println();
        System.out.println("===== Banking System =====");
        System.out.println();
        System.out.println("1. Open Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Find Customer");
        System.out.println("6. Add Customer");
        System.out.println("7. Update Customer");
        System.out.println("8. Delete Customer");
        System.out.println("9. Find Account");
        System.out.println("10. Show Transactions");
        System.out.println("11. Exit");
    }

    private void openAccount() {

        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();

        Customer customer = customerDAO.getCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.print("Enter account type: ");
        String accountType = scanner.next();

        Account account = accountDAO.addAccount(
                BigDecimal.ZERO,
                accountType,
                customerId
        );

        if (account != null) {
            System.out.println("Account opened successfully!");
            System.out.println("Account ID: " + account.getAccountId());
        } else {
            System.out.println("Account creation failed.");
        }
    }

    private void deposit() {

        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();

        Account account = accountDAO.getAccountById(accountId);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter amount: ");
        BigDecimal amount = scanner.nextBigDecimal();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        boolean success = bankService.deposit(
                accountId,
                amount
        );

        if (success) {
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Deposit failed.");
        }
    }

    private void withdraw() {

        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();

        Account account = accountDAO.getAccountById(accountId);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter amount: ");
        BigDecimal amount = scanner.nextBigDecimal();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        boolean success = bankService.withdraw(
                accountId,
                amount
        );

        if (success) {
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println(
                    "Insufficient balance or withdrawal failed."
            );
        }
    }

    private void transfer() {

        System.out.print("Enter sender account ID: ");
        int senderId = scanner.nextInt();

        Account sender = accountDAO.getAccountById(senderId);

        if (sender == null) {
            System.out.println("Sender account not found.");
            return;
        }

        System.out.print("Enter receiver account ID: ");
        int receiverId = scanner.nextInt();

        Account receiver = accountDAO.getAccountById(receiverId);

        if (receiver == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        if (senderId == receiverId) {
            System.out.println(
                    "Sender and receiver cannot be the same account."
            );
            return;
        }

        System.out.print("Enter amount: ");
        BigDecimal amount = scanner.nextBigDecimal();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        boolean success = bankService.transfer(
                senderId,
                receiverId,
                amount
        );

        if (success) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed.");
        }
    }

    private void findCustomer() {

        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();

        Customer customer =
                customerDAO.getCustomerById(customerId);

        if (customer != null) {
            System.out.println(customer);
        } else {
            System.out.println("Customer not found.");
        }
    }

    private void addCustomer() {

        System.out.print("Enter first name: ");
        String firstName = scanner.next();

        System.out.print("Enter last name: ");
        String lastName = scanner.next();

        customerDAO.addCustomer(
                firstName,
                lastName
        );
    }

    private void updateCustomer() {

        System.out.print("Enter customer ID to update: ");
        int customerId = scanner.nextInt();

        System.out.print("Enter new first name: ");
        String firstName = scanner.next();

        System.out.print("Enter new last name: ");
        String lastName = scanner.next();

        customerDAO.updateCustomer(
                customerId,
                firstName,
                lastName
        );
    }

    private void deleteCustomer() {

        System.out.print("Enter customer ID to delete: ");
        int customerId = scanner.nextInt();

        customerDAO.deleteCustomer(customerId);
    }

    private void findAccount() {

        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();

        Account account =
                accountDAO.getAccountById(accountId);

        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("Account not found.");
        }
    }

    private void showTransactions() {

        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();

        Account account =
                accountDAO.getAccountById(accountId);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        transactionDAO.showTransactionsByAccount(accountId);
    }
}