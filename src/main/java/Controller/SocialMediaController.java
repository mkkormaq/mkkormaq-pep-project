package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.*;
import Model.*;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     //define the service objects needed for the handlers at the class level so they can be used by each handler
    AccountService accountService;
    MessageService messageService;
    
    //instantiate the service objects in the constructor
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    //define each endpoint as defined by the user stories in the readme
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::accountLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountHandler);

        return app;
    }


     //Define each handler per the specifications in the user stories
    private void registerAccountHandler(Context context) throws JsonMappingException, JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null){
            context.json(om.writeValueAsString(addedAccount));
        }else{
            
            context.status(400);
        }
        
    }

    
    private void accountLoginHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account verifiedAccount = accountService.verifyAccount(account);
        if(verifiedAccount != null){
            context.json(om.writeValueAsString(verifiedAccount));
        }else{
            context.status(401);
        }
    }


    private void postMessageHandler(Context context) throws JsonMappingException, JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            context.json(om.writeValueAsString(addedMessage));
        }else{
            
            context.status(400);
        }
        
    }

    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }
    
    private void getMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);

        if(message != null){
            context.json(om.writeValueAsString(message));
        }else{
            context.result("");
        }
    }

    //update is unique as we need to obtain the pathParam and the new message from the body
    private void updateMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        String newMessage = om.readValue(context.body(), Message.class).getMessage_text();
        Message message = messageService.updateMessageById(message_id, newMessage);
        if(message == null) {
            context.status(400);
        }else{
            context.json(om.writeValueAsString(message));
        }
    }

    private void deleteMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);

        if(message == null) {
            context.result("");
        }else{
            context.json(om.writeValueAsString(message));
        }
    }

    private void getAllMessagesByAccountHandler(Context context){
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccount(account_id);
        context.json(messages);
    }


}