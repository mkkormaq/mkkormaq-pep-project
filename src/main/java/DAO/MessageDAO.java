package DAO;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.*;
import java.sql.*;

public class MessageDAO {

    //defining each method that directly accesses the db.
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int message_id = rs.getInt("message_id");
                return new Message(message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //started shortenting connection and preparedStatement variables here
    public List<Message> getAllMessages(){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                                rs.getInt("posted_by"),
                                                rs.getString("message_text"), 
                                                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int message_id){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                                rs.getInt("posted_by"),
                                                rs.getString("message_text"), 
                                                rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //could return an int equal to the number of rows affected, but no use for that here
    public void deleteMessageById(int message_id){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message_id);
            ps.executeUpdate();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }

    //same here, could return number of affected rows if needed.
    public void updateMessageById(int message_id, String newMessage){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newMessage);
            ps.setInt(2, message_id);
            ps.executeUpdate();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }

    public List<Message> getAllMessagesByAccount(int account_id){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                                rs.getInt("posted_by"),
                                                rs.getString("message_text"), 
                                                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
