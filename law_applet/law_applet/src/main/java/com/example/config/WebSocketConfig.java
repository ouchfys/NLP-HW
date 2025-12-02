package com.example.config;

import com.example.service.handler.ChatWebSocketHandler;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private OllamaChatModel ollamaChatModel;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(ollamaChatModel), "/ws/chat") // WebSocket 路径
                .addInterceptors(new HttpSessionHandshakeInterceptor()) // 可选的会话拦截器
                .setAllowedOrigins("*"); // 允许所有来源访问
    }

    // 如果你是通过 @ServerEndpoint 进行配置的，可以使用以下 ServerEndpointExporter 类
    // @Bean
    // public ServerEndpointExporter serverEndpointExporter() {
    //     return new ServerEndpointExporter();
    // }
}
