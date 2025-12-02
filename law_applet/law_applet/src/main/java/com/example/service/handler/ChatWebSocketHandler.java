package com.example.service.handler;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;


import org.springframework.ai.chat.messages.*;

import java.util.List;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final OllamaChatModel ollamaChatModel;

    // 构造函数注入模型
    public ChatWebSocketHandler(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 获取客户端发送的消息
        String inputMessage = message.getPayload();

        // 设置 System Prompt，定义 AI 行为
        SystemMessage systemMessage = new SystemMessage("""
            你是一位专业的 AI 助手，专门回答法律相关问题。
            请用清晰、简洁的语言回复，并提供相关法律条文参考，以下是我的问题：
        """);

        // 创建 Prompt 对象，包含 SystemMessage 和 UserMessage
        Prompt prompt = new Prompt(List.of(systemMessage, new UserMessage(inputMessage)));

        // 通过流式响应返回 AI 生成的文本
        ollamaChatModel.stream(prompt)
                .map(response -> response.getResult().getOutput().getText())
                .doOnTerminate(() -> {
                    // 连接关闭时执行的逻辑（可选）
                })
                .subscribe(
                        chunk -> {
                            try {
                                // 发送流式数据给客户端
                                session.sendMessage(new TextMessage(chunk));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            // 发生错误时，返回错误消息
                            try {
                                session.sendMessage(new TextMessage("发生错误：" + error.getMessage()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
    }
}
