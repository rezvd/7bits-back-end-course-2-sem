package it.sevenbits.hw_servlets.session;

import it.sevenbits.hw_servlets.task.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletSession extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SessionRepository sessions = SessionRepository.getInstance();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String id = sessions.addUser(name);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        Cookie cookie = new Cookie("session-id", id);
        cookie.setComment("ID of current user");
        cookie.setMaxAge(24*60*60);
        cookie.setPath("hw/session");
        resp.addCookie(cookie);
        resp.getWriter().write(id);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SessionRepository sessions = SessionRepository.getInstance();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String id = "";
        for (Cookie item: req.getCookies()) {
            if(item.getName().equals("session-id")) {
                id = item.getValue();
            }
        }
        if (sessions.isSessionExists(id)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(String.format("<html><body><h1>Current user is %s</h1></body></html>", sessions.getUser(id)));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("<html><body><h1>User not found</h1></body></html>");
        }
    }

}
