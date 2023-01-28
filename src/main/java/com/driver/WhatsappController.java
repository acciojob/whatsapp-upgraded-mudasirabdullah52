package com.driver;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("whatsapp")
public class WhatsappController {
    WhatsappService whatsappService = new WhatsappService();

    @PostMapping("/add-user")
    public String createUser(String name,String mobile) throws Exception {
        return whatsappService.createUser(name, mobile);
    }

    @PostMapping("/add-group")
    public Group createGroup(List<User> users){
        return whatsappService.createGroup(users);
    }

    @PostMapping("/add-message")
    public int createMessage(String content){
        return whatsappService.createMessage(content);
    }

    @PutMapping("/send-message")
    public int sendMessage(Message message, User sender, Group group) throws Exception{
        int response = whatsappService.sendMessage(message, sender, group);
        if (response == -1){
            throw new Exception("Group does not exist");
        } else if (response == -2) {
            throw new Exception("You are not allowed to send message");
        }
        return response;
    }
    @PutMapping("/change-admin")
    public String changeAdmin(User approver, User user, Group group) throws Exception{
        String response = whatsappService.changeAdmin(approver, user, group);
        if (response.equals("Group not exists")){
            throw new Exception("Group does not exist");
        } else if (response.equals("not an admin")) {
            throw new Exception("Approver does not have rights");
        } else if (response.equals("not a user")) {
            throw new Exception("User is not a participant");
        }
        return response;
    }

    @DeleteMapping("/remove-user")
    public int removeUser(User user) throws Exception{
        return whatsappService.removeUser(user);
    }

    @GetMapping("/find-messages")
    public String findMessage(Date start, Date end, int K) throws Exception{
        if (whatsappService.findMessage(start,end,K) == null){
            throw new Exception("K is greater than the number of messages");
        }
        return whatsappService.findMessage(start, end, K);
    }
}