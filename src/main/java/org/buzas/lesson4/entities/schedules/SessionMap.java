package org.buzas.lesson4.entities.schedules;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class SessionMap {
    private final HashMap<Long, Session> sessions = new HashMap<>();

    public void addSession(Session session) {
        sessions.put(session.getId(), session);
    }

    public Session getSession(Long id) {
        return sessions.get(id);
    }

    public void updateSession(Long id, Session session) {
        sessions.put(id, session);
    }

    public void deleteSession(Long id) {
        sessions.remove(id);
    }
}
