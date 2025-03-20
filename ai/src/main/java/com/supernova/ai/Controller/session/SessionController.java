package com.supernova.ai.Controller.session;

import com.supernova.ai.Service.session.SessionService;
import org.springframework.web.bind.annotation.*;

@RestController
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/set-session")
    public String setSessionAttribute(@RequestParam String key, @RequestParam String value) {
        sessionService.setAttribute(key, value);
        return "Session attribute set successfully!";
    }

    @GetMapping("/get-session")
    public Object getSessionAttribute(@RequestParam String key) {
        return sessionService.getAttribute(key);
    }

    @DeleteMapping("/remove-session")
    public String removeSessionAttribute(@RequestParam String key) {
        sessionService.removeAttribute(key);
        return "Session attribute removed!";
    }
}