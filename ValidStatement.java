
    public class ValidStatement {

    public static String buildStatement(Account account) {

        StringBuilder statement = new StringBuilder();
        statement.append("\n");
        statement.append("           ACCOUNT STATEMENT\n");
        statement.append("\n");
        statement.append("Account Number: ").append(account.getAccountNumber()).append("\n");
        statement.append("Account Holder: ").append(account.getOwnerName()).append("\n");
        statement.append("Balance: Rs. ").append(account.getBalance()).append("\n");
        statement.append("Status: ").append(account.getActivity() ? "Active" : "Inactive").append("\n");
        statement.append("\n\n");

        return statement.toString();
        }
    }