package controllers;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProjectDAO;
import dao.StatusDAO;
import dao.TaskDAO;
import dto.TaskDTO;
import models.Task;
import services.UserService;

@WebServlet(name = "taskController", urlPatterns = {"/task-table", "/task-add", "/task-edit", "/task-delete", "/task-details"})
public class TaskServlet extends HttpServlet {
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("currentUser") == null) {
            resp.sendRedirect(req.getContextPath() +"/login");
            return;
        }

        String path = req.getServletPath();
        switch (path) {
            case "/task-table": {
                String keywords = req.getParameter("keywords");
                String pageParam = req.getParameter("page");
                
                int size = 5;
                int page = 1;
                if (pageParam != null) {
                    page = Integer.parseInt(pageParam);
                }
                if (keywords == null) {
                    keywords = "";
                }
                
                int totalRow = taskDAO.countTasks(keywords);
                int totalPage = (totalRow + size - 1) / size;
                
                req.setAttribute("totalPage", totalPage);
                req.setAttribute("page", page);
                req.setAttribute("list", taskDAO.getTaskPaging(keywords, size, page));
                
                req.getRequestDispatcher("/views/task-table.jsp").forward(req, resp);
                break;
            }
            case "/task-add": {
                req.setAttribute("projects", new ProjectDAO().getAll());
                req.setAttribute("users", new UserService().getAllUser());
                req.setAttribute("statuses", new StatusDAO().getAll());
                
                req.getRequestDispatcher("/views/task-add.jsp").forward(req, resp);
                break;
            }
            case "/task-edit": {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("task", taskDAO.findById(id));
                req.setAttribute("projects", new ProjectDAO().getAll());
                req.setAttribute("users", new UserService().getAllUser());
                req.setAttribute("statuses", new StatusDAO().getAll());
                
                req.getRequestDispatcher("/views/task-edit.jsp").forward(req, resp);
                break;
            }
            case "/task-delete": {
                int id = Integer.parseInt(req.getParameter("id"));
                taskDAO.deleteById(id);
                resp.sendRedirect(req.getContextPath() + "/task-table");
                break;
            }
            case "/task-details": {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("taskDTO", taskDAO.findTaskDTOById(id));
                
                req.getRequestDispatcher("/views/task-details.jsp").forward(req, resp);
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        switch (path) {
            case "/task-add": {
                String taskName = req.getParameter("taskName");
                Date startDate = Date.valueOf(req.getParameter("startDate"));
                Date endDate = Date.valueOf(req.getParameter("endDate"));
                int projectId = Integer.parseInt(req.getParameter("projectId"));
                int userId = Integer.parseInt(req.getParameter("userId"));
                int statusId = Integer.parseInt(req.getParameter("statusId"));

                taskDAO.insert(taskName, startDate, endDate, userId, projectId, statusId);

                resp.sendRedirect(req.getContextPath() + "/task-table");
                break;
            }
            case "/task-edit": {
                int id = Integer.parseInt(req.getParameter("id"));
                String taskName = req.getParameter("taskName");
                Date startDate = Date.valueOf(req.getParameter("startDate"));
                Date endDate = Date.valueOf(req.getParameter("endDate"));
                int projectId = Integer.parseInt(req.getParameter("projectId"));
                int userId = Integer.parseInt(req.getParameter("userId"));
                int statusId = Integer.parseInt(req.getParameter("statusId"));

                taskDAO.update(id, taskName, startDate, endDate, userId, projectId, statusId);

                resp.sendRedirect(req.getContextPath() + "/task-table");
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + path);
        }
    }
}