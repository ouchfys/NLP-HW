package com.example.controller;


import com.example.util.R;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private OllamaChatModel ollamaChatModel;

    @GetMapping("/message")
    public R<String> message(@RequestParam("message") String message) {
        String call = ollamaChatModel.call(message);
        return R.ok(call);
    }

}
