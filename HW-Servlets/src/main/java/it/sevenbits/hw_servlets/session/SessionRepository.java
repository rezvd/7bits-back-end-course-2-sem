package it.sevenbits.hw_servlets.session;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionRepository {

    private static SessionRepository instance;
    private Map<String, String> sessions;

    private SessionRepository() {
        sessions = new ConcurrentHashMap<String, String>();
    }

    public static SessionRepository getInstance() {
        if (instance == null) {
            instance = new SessionRepository();
        }
        return instance;
    }

    public String addUser(String name) {
        String id = UUID.randomUUID().toString();
        sessions.put(id, name);
        return id;
    }

    public String getUser(String id) {
        return sessions.get(id);
    }

    public boolean isSessionExists(String id) {
        return sessions.containsKey(id);
    }

    public void delete(String id) {
        sessions.remove(id);
    }

    public Set<Map.Entry<String, String>> getEntrySet() {
        return sessions.entrySet();
    }
}
