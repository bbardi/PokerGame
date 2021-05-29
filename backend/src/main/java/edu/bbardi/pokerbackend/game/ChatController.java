package edu.bbardi.pokerbackend.game;

import edu.bbardi.pokerbackend.game.model.dto.MessageDTO;
import edu.bbardi.pokerbackend.game.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import static edu.bbardi.pokerbackend.UrlMapping.SEND_MESSAGE;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;

    @MessageMapping(SEND_MESSAGE)
    public void sendMessage(MessageDTO messageRequest){
        chatService.sendMessage(messageRequest);
    }
}
