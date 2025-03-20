package com.supernova.ai.Service.session;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final HttpSession session;

    public SessionService(HttpSession session) {
        this.session = session;
    }

    public void setAttribute(String key, Object value) {
        session.setAttribute(key, value);
    }

    public Object getAttribute(String key) {
        return session.getAttribute(key);
    }

    public void removeAttribute(String key) {
        session.removeAttribute(key);
    }

    public void invalidate() {
        session.invalidate();
    }
}