package Service;

import Model.*;
import DAO.*;
import java.util.*;


public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){
        AccountDAO accountDAO = new AccountDAO(); //not sure if this is right, but instantiated AccountDAO to verify account by id
        int posted_by = message.getPosted_by();
        String message_text = message.getMessage_text();

        if ((message_text.length() > 0) && (message_text.length() < 255) && (accountDAO.getAccountById(posted_by) != null)){
            return messageDAO.insertMessage(message);
        }else{
            return null;
        }
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

}
