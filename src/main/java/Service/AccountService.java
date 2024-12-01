package Service;

import Model.*;
import DAO.*;
import java.util.*;


public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        if ((username.length() > 0) && (password.length() >=4 )&& (accountDAO.getAccountByUsername(account) == null)){
            return accountDAO.insertAccount(account);
        }else{
            return null;
        }
    }

    public Account verifyAccount(Account account){
        return accountDAO.verifyAccount(account);
    }
}
