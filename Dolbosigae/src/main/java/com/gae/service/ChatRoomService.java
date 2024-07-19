package com.gae.service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {
    private final Map<String, Set<String>> chatRooms = new ConcurrentHashMap<>();

    // 채팅방 생성
    public synchronized boolean createRoom(String roomId, String userA, String userB) {
        if (chatRooms.containsKey(roomId)) {
            return false; // 방이 이미 존재함
        }

        Set<String> users = ConcurrentHashMap.newKeySet();
        users.add(userA);
        users.add(userB);
        chatRooms.put(roomId, users);

        return true;
    }

    // 방에 회원 추가
    public synchronized boolean addUserToRoom(String roomId, String username) {
        Set<String> users = chatRooms.get(roomId);
        if (users == null) {
            return false; // 방이 존재하지 않음
        }

        if (users.size() >= 2) {
            return false; // 방이 꽉 찼음
        }

        return users.add(username);
    }

    // 방에서 회원 지움 
    public synchronized void removeUserFromRoom(String roomId, String username) {
        Set<String> users = chatRooms.get(roomId);
        if (users != null) {
            users.remove(username);
            if (users.isEmpty()) {
                chatRooms.remove(roomId); // 사용자가 모두 나가면 채팅방 삭제
            }
        }
    }

    // 채팅방 삭제
    public synchronized void removeChatRoom(String roomId) {
        chatRooms.remove(roomId);
    }

    // 채팅방에 있는 사용자 목록 조회
    public Set<String> getUsersInRoom(String roomId) {
        return chatRooms.get(roomId);
    }
}
