package Service;

import Model.*;
import DAO.*;


public class AccountService {

    //singlton AccountDAO
    private AccountDAO accountDAO;

    //constructor to instantiate AccountDAO
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    //overloaded constructor in case AccountDAO has already been instantiated
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    //methods for defining business logic for each request.
    public Account addAccount(Account account){
        String username = account.getUsername();
        String password = account.getPassword();

        //utilized a method for retrieving an account by username instead of verifyAccount.
        if ((username.length() > 0) && (password.length() >=4 ) && (accountDAO.getAccountByUsername(account) == null)){
            return accountDAO.insertAccount(account);
        }else{
            return null;
        }
    }

    //wanted to preserve this method in case session logic needed to be added in the future.
    public Account verifyAccount(Account account){
        return accountDAO.verifyAccount(account);
    }
}
