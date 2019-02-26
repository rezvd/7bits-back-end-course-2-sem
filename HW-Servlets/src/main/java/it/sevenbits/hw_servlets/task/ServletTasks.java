package it.sevenbits.hw_servlets.task;

import it.sevenbits.hw_servlets.session.SessionRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ServletTasks extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionRepository sessions = SessionRepository.getInstance();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String auth = req.getHeader("Authorization");
        if (auth == null || !sessions.isSessionExists(auth)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("\"Session ID is not correct or it haven't been set\"");
            return;
        }
        TasksRepository tasks = TasksRepository.getInstance();
        StringBuilder result = new StringBuilder();
        result.append("[\n");
        for (Map.Entry entry : tasks.getEntrySet()) {
            result.append("\t{ \"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\" },\n");
        }
        result.deleteCharAt(result.lastIndexOf(","));
        result.append("]");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionRepository sessions = SessionRepository.getInstance();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String auth = req.getParameter("Authorization");
        if (!sessions.isSessionExists(auth)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("\"Session ID is not correct or it haven't been set\"");
            return;
        }
        TasksRepository tasks = TasksRepository.getInstance();
        String name = req.getParameter("name");
        String id = tasks.addTask(name);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(String.format("{\n\"id\": \"%s\",\n\"name\": \"%s\"\n}", id, name));
    }
}