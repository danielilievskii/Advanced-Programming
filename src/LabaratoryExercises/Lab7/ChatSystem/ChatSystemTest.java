package LabaratoryExercises.Lab7.ChatSystem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;
import java.util.stream.Collectors;

class NoSuchRoomException extends Exception {
    public NoSuchRoomException(String message) {
        super(message + "EXCEPTION");
    }
}

class NoSuchUserException extends Exception {
    public NoSuchUserException(String message) {
        super(message + "EXCEPTION");
    }
}
class User {
    String username;
    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
class ChatRoom {
    String chatRoomName;
    Set<String> users;
    public ChatRoom(String name) {
        this.chatRoomName = name;
        this.users = new TreeSet<>();
    }

    public void addUser(String username) {
        //User user = new User(username);
        users.add(username);
    }
    public void removeUser(String username) {
        if(users.contains(username)) {
            users.remove(username);
        }
    }
    public boolean hasUser(String username) {
        if(users.contains(username)) {
            return true;
        }
        return false;
    }

    public int numUsers() {
        return users.size();
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s\n", chatRoomName));
        if(!users.isEmpty()) {
            users.forEach(sb::append);
        } else {
            sb.append("EMPTY\n");
        }
        return sb.toString();
    }
}

class ChatSystem {
    Map<String, ChatRoom> chatRooms;
    Set<String> users;

    public ChatSystem() {
        chatRooms = new TreeMap<>();
        users = new HashSet<>();
    }
    public void addRoom(String roomName) {
        chatRooms.put(roomName, new ChatRoom(roomName));
    }
    public void removeRoom(String roomName) {
        chatRooms.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
        if(!chatRooms.containsKey(roomName)) {
            throw new NoSuchRoomException(roomName);
        }
        return chatRooms.get(roomName);
    }

    public void register(String userName) {
        ChatRoom room = chatRooms.values().stream().min(Comparator.comparing(ChatRoom::numUsers).thenComparing(ChatRoom::getChatRoomName)).orElse(null);
        users.add(userName);
        if(room!=null) {
            room.addUser(userName);
        }
    }

    public void registerAndJoin(String userName, String roomName) {
        ChatRoom room = chatRooms.get(roomName);
        users.add(userName);
        room.addUser(userName);
    }

    public void joinRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException{
        if(!chatRooms.containsKey(roomName)) {
            throw new NoSuchRoomException(roomName);
        }

        if(!users.contains(userName)) {
            throw new NoSuchUserException(userName);
        }

        chatRooms.get(roomName).addUser(userName);
    }

    public void leaveRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {
        if(!chatRooms.containsKey(roomName)) {
            throw new NoSuchRoomException(roomName);
        }
        if(!users.contains(userName)) {
            throw new NoSuchUserException(userName);
        }
        chatRooms.get(roomName).removeUser(userName);
    }
    public void followFriend(String userName, String friend_userName) throws NoSuchUserException {
        if(!users.contains(userName)) {
            throw new NoSuchUserException(userName);
        }
        if(!users.contains(friend_userName)) {
            throw new NoSuchUserException(friend_userName);
        }

        List<ChatRoom> friendRooms = chatRooms.values().stream().filter(cr -> cr.hasUser(friend_userName)).collect(Collectors.toList());
        friendRooms.forEach(room -> {
            chatRooms.get(room).addUser(userName);
        });

//        for(ChatRoom room : chatRooms.values()) {
//            if(room.hasUser(friend_userName)) {
//                room.addUser(userName);
//            }
//        }

    }
}

public class ChatSystemTest {
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr.addUser(jin.next());
                if (k == 1) cr.removeUser(jin.next());
                if (k == 2) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if (n == 0) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr2.addUser(jin.next());
                if (k == 1) cr2.removeUser(jin.next());
                if (k == 2) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if (k == 1) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while (true) {
                String cmd = jin.next();
                if (cmd.equals("stop")) break;
                if (cmd.equals("print")) {
                    System.out.println(cs.getRoom(jin.next()) + "\n");
                    continue;
                }
                for (Method m : mts) {
                    if (m.getName().equals(cmd)) {
                        String params[] = new String[m.getParameterTypes().length];
                        for (int i = 0; i < params.length; ++i) params[i] = jin.next();
                        m.invoke(cs, (Object) params);
                    }
                }
            }
        }
    }
}

