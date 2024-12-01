package DAO;

import java.sql.PreparedStatement;
import java.sql.Statement;

import Model.Account;
import Util.*;
import java.sql.*;

public class AccountDAO {
    
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int account_id = rs.getInt("account_id");
                return new Account(account_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account getAccountByUsername(Account account){
        Connection conn = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account resAccount = new Account(rs.getInt("account_id") , rs.getString("username"), rs.getString("password"));
                return resAccount;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int account_id){
        Connection conn = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account resAccount = new Account(rs.getInt("account_id") , rs.getString("username"), rs.getString("password"));
                return resAccount;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account verifyAccount(Account account){
        Connection conn = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account resAccount = new Account(rs.getInt("account_id") , rs.getString("username"), rs.getString("password"));
                return resAccount;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
