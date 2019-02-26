package it.sevenbits.w1_servlets;


import it.sevenbits.w1_servlets.repository.TasksRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RootServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        TasksRepository tasks = TasksRepository.getInstance();
        String id = req.getParameter("id");
        if (id != null && tasks.isTaskExist(id)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(String.format("{ \"task\": \"%s\" } ", tasks.getTask(id)));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("\"Task not found\"");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        TasksRepository tasks = TasksRepository.getInstance();
        String id = req.getParameter("id");
        if (id != null && tasks.isTaskExist(id)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            tasks.delete(id);
            resp.getWriter().write(String.format("{ \"id\": \"%s\" }", id));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("\"Task not found\"");
        }
    }
}