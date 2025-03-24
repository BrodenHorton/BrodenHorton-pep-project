package Service;

import Model.Account;
import DAO.AccountDAO;;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account getAccount(String username) {
        return accountDAO.getAccountByUsername(username);
    }

    public Account getAccount(String username, String password) {
        return accountDAO.getAccountByUsernameAndPassword(username, password);
    }

    public Account addAccount(Account account) {
        if(account.getUsername() == null || account.getUsername().isEmpty() || getAccount(account.getUsername()) != null) {
            return null;
        }
        if(account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }

        return accountDAO.insertAccount(account);
    }
}
