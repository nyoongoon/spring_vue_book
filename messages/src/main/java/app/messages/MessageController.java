package app.messages;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MessageController {
    MessageService messageService;
    MessageController(MessageService messageService){
        this.messageService = messageService;
    }
    /*@GetMapping("/welcome")
    public String welcome(Model model){
        model.addAttribute("message", "hello, Welcome to Spring Boot!");
        return "welcome";
    }*/
    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Message> saveMessage(@RequestBody MessageData data){
        //매개변수에 @RequestBody 붙이면 HTTP요청본문에 전달된 JSON형식 string을 DTO 인스턴스로 변환함!!
        Message saved = messageService.save(data.getText());
        if(saved == null){
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(saved);
    }

}
