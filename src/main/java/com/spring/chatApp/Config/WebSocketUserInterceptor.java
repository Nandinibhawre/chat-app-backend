package com.spring.chatApp.Config;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import java.security.Principal;

@Component
public class WebSocketUserInterceptor
        implements ChannelInterceptor {

    @Override
    public Message<?> preSend(
            Message<?> message,
            MessageChannel channel
    ) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(
                        message,
                        StompHeaderAccessor.class
                );

        if (
                StompCommand.CONNECT.equals(
                        accessor.getCommand()
                )
        ) {

            String email =
                    accessor.getFirstNativeHeader(
                            "userEmail"
                    );

            accessor.setUser(
                    new Principal() {

                        @Override
                        public String getName() {

                            return email;
                        }
                    }
            );
        }

        return message;
    }
}