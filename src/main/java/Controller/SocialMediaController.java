package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.*;
import Model.*;

import java.util.List;

import com.fasterxml.jackson.*;
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

    AccountService accountService;
    MessageService messageService;
    
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::accountLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        // app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        // app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        // app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountHandler);

        return app;
    }


     //This is the handler for processing new account registration endpoint.
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

    //this handler will validate that the account info sent in the body exists identically in the db
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
        if(message == null) {
            context.result("");
        }else{
            context.json(om.writeValueAsString(message));
        }
    }
}