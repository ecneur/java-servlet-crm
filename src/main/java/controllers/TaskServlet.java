package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
import models.User;
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
        if (session == null || session.getAttribute("currentUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();
        switch (path) {
            case "/task-table": {
                User user = (User) session.getAttribute("currentUser");
                List<TaskDTO> tasks = new ArrayList<>();

                if (user.getRoleId() == 1) {
                    tasks = taskDAO.getAllTasksDTO();
                } else if (user.getRoleId() == 2) {
                    tasks = taskDAO.getTasksByManager(user.getId());
                } else if (user.getRoleId() == 3) {
                    tasks = taskDAO.getTasksByEmployee(user.getId());
                }
                
                String keywords = req.getParameter("keywords");
                if (keywords != null && !keywords.trim().isEmpty()) {
                    String kw = keywords.trim().toLowerCase();
                    tasks = tasks.stream()
                            .filter(t -> t.getTaskName().toLowerCase().contains(kw))
                            .collect(java.util.stream.Collectors.toList());
                }

                req.setAttribute("totalPage", 1);
                req.setAttribute("page", 1);
                req.setAttribute("list", tasks);
                
                req.getRequestDispatcher("/views/task-table.jsp").forward(req, resp);
                break;
            }
            case "/task-add": {
                models.User currentUser = (models.User) req.getSession().getAttribute("currentUser");
                if (currentUser.getRoleId() == 1) {
                    req.setAttribute("projects", new dao.ProjectDAO().getAll());
                } else {
                    req.setAttribute("projects", new dao.ProjectDAO().getProjectsByManager(currentUser.getId()));
                }
                
                req.setAttribute("users", new services.UserService().getAllUser());
                req.setAttribute("statuses", new dao.StatusDAO().getAll());
                req.getRequestDispatcher("/views/task-add.jsp").forward(req, resp);
                break;
            }
            case "/task-edit": {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("task", taskDAO.findById(id));
                
                models.User currentUser = (models.User) req.getSession().getAttribute("currentUser");
                if (currentUser.getRoleId() == 1) {
                    req.setAttribute("projects", new dao.ProjectDAO().getAll());
                } else {
                    req.setAttribute("projects", new dao.ProjectDAO().getProjectsByManager(currentUser.getId()));
                }
                
                req.setAttribute("users", new services.UserService().getAllUser());
                req.setAttribute("statuses", new dao.StatusDAO().getAll());
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
                req.setAttribute("task", taskDAO.findById(id));
                req.setAttribute("projects", new dao.ProjectDAO().getAll());
                req.setAttribute("users", new services.UserService().getAllUser());
                req.setAttribute("statuses", new dao.StatusDAO().getAll());
                
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
	            models.User currentUser = (models.User) req.getSession().getAttribute("currentUser");
	            java.util.List<models.Project> projectList = (currentUser.getRoleId() == 1) ? 
	                    new dao.ProjectDAO().getAll() : new dao.ProjectDAO().getProjectsByManager(currentUser.getId());
	
	            String taskName = req.getParameter("taskName");
	            int projectId = Integer.parseInt(req.getParameter("projectId"));
	            int userId = Integer.parseInt(req.getParameter("userId"));
	            int statusId = Integer.parseInt(req.getParameter("statusId"));
	            java.sql.Date startDate = null;
	            java.sql.Date endDate = null;
	
	            try {
	                startDate = java.sql.Date.valueOf(req.getParameter("startDate"));
	                endDate = java.sql.Date.valueOf(req.getParameter("endDate"));
	            } catch (IllegalArgumentException e) {
	                req.setAttribute("error", "Dinh dang ngay khong hop le!");
	                req.setAttribute("task", new models.Task(0, taskName, startDate, endDate, statusId, userId, projectId));
	                req.setAttribute("projects", projectList);
	                req.setAttribute("users", new services.UserService().getAllUser());
	                req.setAttribute("statuses", new dao.StatusDAO().getAll());
	                req.getRequestDispatcher("/views/task-add.jsp").forward(req, resp);
	                return;
	            }
	
	            if (endDate.before(startDate)) {
	                req.setAttribute("error", "Ngay ket thuc khong duoc truoc ngay bat dau!");
	                req.setAttribute("task", new models.Task(0, taskName, startDate, endDate, statusId, userId, projectId));
	                req.setAttribute("projects", projectList);
	                req.setAttribute("users", new services.UserService().getAllUser());
	                req.setAttribute("statuses", new dao.StatusDAO().getAll());
	                req.getRequestDispatcher("/views/task-add.jsp").forward(req, resp);
	                return;
	            }
	
	            models.Project parentProject = new dao.ProjectDAO().findById(projectId);
	            if (parentProject != null) {
	                if (startDate.before(parentProject.getStartDate()) || endDate.after(parentProject.getEndDate())) {
	                    req.setAttribute("error", "Thoi gian Task phai nam trong khoang thoi gian Du an (" + parentProject.getStartDate() + " den " + parentProject.getEndDate() + ")!");
	                    req.setAttribute("task", new models.Task(0, taskName, startDate, endDate, statusId, userId, projectId));
	                    req.setAttribute("projects", projectList);
	                    req.setAttribute("users", new services.UserService().getAllUser());
	                    req.setAttribute("statuses", new dao.StatusDAO().getAll());
	                    req.getRequestDispatcher("/views/task-add.jsp").forward(req, resp);
	                    return;
	                }
	            }
	
	            taskDAO.insert(taskName, startDate, endDate, userId, projectId, statusId);
	            resp.sendRedirect(req.getContextPath() + "/task-table?add=1");
	            break;
	        }
	        case "/task-edit": {
	            models.User currentUser = (models.User) req.getSession().getAttribute("currentUser");
	            java.util.List<models.Project> projectList = (currentUser.getRoleId() == 1) ? 
	                    new dao.ProjectDAO().getAll() : new dao.ProjectDAO().getProjectsByManager(currentUser.getId());
	
	            int id = Integer.parseInt(req.getParameter("id"));
	            String taskName = req.getParameter("taskName");
	            int projectId = Integer.parseInt(req.getParameter("projectId"));
	            int userId = Integer.parseInt(req.getParameter("userId"));
	            int statusId = Integer.parseInt(req.getParameter("statusId"));
	            java.sql.Date startDate = null;
	            java.sql.Date endDate = null;
	
	            try {
	                startDate = java.sql.Date.valueOf(req.getParameter("startDate"));
	                endDate = java.sql.Date.valueOf(req.getParameter("endDate"));
	            } catch (IllegalArgumentException e) {
	                req.setAttribute("error", "Dinh dang ngay khong hop le!");
	                req.setAttribute("task", new models.Task(id, taskName, startDate, endDate, statusId, userId, projectId));
	                req.setAttribute("projects", projectList);
	                req.setAttribute("users", new services.UserService().getAllUser());
	                req.setAttribute("statuses", new dao.StatusDAO().getAll());
	                req.getRequestDispatcher("/views/task-edit.jsp").forward(req, resp);
	                return;
	            }
	
	            if (endDate.before(startDate)) {
	                req.setAttribute("error", "Ngay ket thuc khong duoc truoc ngay bat dau!");
	                req.setAttribute("task", new models.Task(id, taskName, startDate, endDate, statusId, userId, projectId));
	                req.setAttribute("projects", projectList);
	                req.setAttribute("users", new services.UserService().getAllUser());
	                req.setAttribute("statuses", new dao.StatusDAO().getAll());
	                req.getRequestDispatcher("/views/task-edit.jsp").forward(req, resp);
	                return;
	            }
	
	            models.Project parentProject = new dao.ProjectDAO().findById(projectId);
	            if (parentProject != null) {
	                if (startDate.before(parentProject.getStartDate()) || endDate.after(parentProject.getEndDate())) {
	                    req.setAttribute("error", "Thoi gian Task phai nam trong khoang thoi gian Du an (" + parentProject.getStartDate() + " den " + parentProject.getEndDate() + ")!");
	                    req.setAttribute("task", new models.Task(id, taskName, startDate, endDate, statusId, userId, projectId));
	                    req.setAttribute("projects", projectList);
	                    req.setAttribute("users", new services.UserService().getAllUser());
	                    req.setAttribute("statuses", new dao.StatusDAO().getAll());
	                    req.getRequestDispatcher("/views/task-edit.jsp").forward(req, resp);
	                    return;
	                }
	            }
	
	            taskDAO.update(id, taskName, startDate, endDate, userId, projectId, statusId);
	            resp.sendRedirect(req.getContextPath() + "/task-table?update=1");
	            break;
	        }
            case "/task-details": {
                int id = Integer.parseInt(req.getParameter("id"));
                int statusId = Integer.parseInt(req.getParameter("statusId"));
                
                models.Task existingTask = taskDAO.findById(id);
                if (existingTask != null) {
                    taskDAO.update(id, existingTask.getTaskName(), existingTask.getStartDate(), 
                                   existingTask.getEndDate(), existingTask.getUserId(), 
                                   existingTask.getProjectId(), statusId);
                }
                
                resp.sendRedirect(req.getContextPath() + "/task-table?update=1");
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + path);
        }
    }
}