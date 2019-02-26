package it.sevenbits.w1_servlets;


import it.sevenbits.w1_servlets.repository.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class RootServletForTasks extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        TasksRepository tasks = TasksRepository.getInstance();
        StringBuilder result = new StringBuilder();
        result.append("[\n");
        for (Map.Entry entry : tasks.getEntrySet()) {
            result.append("\t{ \"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\" },\n");
        }
        if(result.lastIndexOf(",") >= 0) {
            result.deleteCharAt(result.lastIndexOf(","));
        }
        result.append("]");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        TasksRepository tasks = TasksRepository.getInstance();
        String name = req.getParameter("name");
        String id = tasks.addTask(name);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(String.format("{\n\"id\": \"%s\",\n\"name\": \"%s\"\n}", id, name));
    }
}