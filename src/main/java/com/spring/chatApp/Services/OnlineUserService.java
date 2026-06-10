package com.spring.chatApp.Services;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OnlineUserService {

    private final Set<String> onlineUsers =
            ConcurrentHashMap.newKeySet();

//    public void userOnline(String email) {
//
//        onlineUsers.add(email);
//    }
//
//    public void userOffline(String email) {
//
//        onlineUsers.remove(email);
//    }
//
//    public boolean isOnline(String email) {
//
//        return onlineUsers.contains(email);
//    }
//
//    public Set<String> getOnlineUsers() {
//
//        return onlineUsers;
//    }
//}
private final Map<String, LocalDateTime> lastSeenMap =
        new ConcurrentHashMap<>();


    public void userOnline(String email) {

        onlineUsers.add(email);
    }


    public void userOffline(String email) {

        onlineUsers.remove(email);

        lastSeenMap.put(
                email,
                LocalDateTime.now(
                        ZoneId.of("Asia/Kolkata")
                )
        );
    }


    public boolean isOnline(String email) {

        return onlineUsers.contains(email);
    }


    public LocalDateTime getLastSeen(String email) {

        return lastSeenMap.get(email);
    }
}