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
import models.Project;
import models.User;

@WebServlet(name = "projectController", urlPatterns = {"/groupwork-table", "/groupwork-add", "/groupwork-edit", "/groupwork-delete", "/groupwork-details"})
public class ProjectServlet extends HttpServlet {
    private ProjectDAO projectDAO;

    @Override
    public void init() throws ServletException {
        projectDAO = new ProjectDAO();
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
            case "/groupwork-table": {
                User user = (User) session.getAttribute("currentUser");
                List<Project> projects = new ArrayList<>();

                if (user.getRoleId() == 1) { 
                    projects = projectDAO.getAll();
                } else if (user.getRoleId() == 2) { 
                    projects = projectDAO.getProjectsByManager(user.getId());
                }
                
                String keywords = req.getParameter("keywords");
                if (keywords != null && !keywords.trim().isEmpty()) {
                    String kw = keywords.trim().toLowerCase();
                    projects = projects.stream()
                            .filter(p -> p.getProjectName().toLowerCase().contains(kw))
                            .collect(java.util.stream.Collectors.toList());
                }

                req.setAttribute("totalPage", 1);
                req.setAttribute("page", 1);
                req.setAttribute("list", projects);
                
                req.getRequestDispatcher("/views/groupwork-table.jsp").forward(req, resp);
                break;
            }
            case "/groupwork-add": {
                req.getRequestDispatcher("/views/groupwork-add.jsp").forward(req, resp);
                break;
            }
            case "/groupwork-edit": {
                int id = Integer.parseInt(req.getParameter("id"));
                Project project = projectDAO.findById(id);
                req.setAttribute("project", project);
                req.getRequestDispatcher("/views/groupwork-edit.jsp").forward(req, resp);
                break;
            }
            case "/groupwork-delete": {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean isSuccess = projectDAO.deleteById(id);
                if (isSuccess) {
                    resp.sendRedirect(req.getContextPath() + "/groupwork-table?delete=1");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/groupwork-table?err=1");
                }
                break;
            }
            case "/groupwork-details": {
                int id = Integer.parseInt(req.getParameter("id"));
                Project project = projectDAO.findById(id);
                req.setAttribute("project", project);
                req.getRequestDispatcher("/views/groupwork-details.jsp").forward(req, resp);
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
	        case "/groupwork-add": {
	            String name = req.getParameter("projectName");
	            java.sql.Date startDate = null;
	            java.sql.Date endDate = null;
	
	            try {
	                startDate = java.sql.Date.valueOf(req.getParameter("startDate"));
	                endDate = java.sql.Date.valueOf(req.getParameter("endDate"));
	            } catch (IllegalArgumentException e) {
	                req.setAttribute("error", "Dinh dang ngay khong hop le!");
	                req.setAttribute("project", new models.Project(0, name, startDate, endDate));
	                req.getRequestDispatcher("/views/groupwork-add.jsp").forward(req, resp);
	                return;
	            }
	
	            if (endDate.before(startDate)) {
	                req.setAttribute("error", "Ngay ket thuc khong duoc truoc ngay bat dau!");
	                req.setAttribute("project", new models.Project(0, name, startDate, endDate));
	                req.getRequestDispatcher("/views/groupwork-add.jsp").forward(req, resp);
	                return;
	            }
	
	            models.User user = (models.User) req.getSession().getAttribute("currentUser");
	            projectDAO.insert(name, startDate, endDate, user.getId());
	
	            resp.sendRedirect(req.getContextPath() + "/groupwork-table?add=1");
	            break;
	        }
	        case "/groupwork-edit": {
	            int id = Integer.parseInt(req.getParameter("id"));
	            String name = req.getParameter("projectName");
	            java.sql.Date startDate = null;
	            java.sql.Date endDate = null;
	
	            try {
	                startDate = java.sql.Date.valueOf(req.getParameter("startDate"));
	                endDate = java.sql.Date.valueOf(req.getParameter("endDate"));
	            } catch (IllegalArgumentException e) {
	                req.setAttribute("error", "Dinh dang ngay khong hop le!");
	                req.setAttribute("project", new models.Project(id, name, startDate, endDate));
	                req.getRequestDispatcher("/views/groupwork-edit.jsp").forward(req, resp);
	                return;
	            }
	
	            if (endDate.before(startDate)) {
	                req.setAttribute("error", "Ngay ket thuc khong duoc truoc ngay bat dau!");
	                req.setAttribute("project", new models.Project(id, name, startDate, endDate));
	                req.getRequestDispatcher("/views/groupwork-edit.jsp").forward(req, resp);
	                return;
	            }
	
	            projectDAO.update(id, name, startDate, endDate);
	            resp.sendRedirect(req.getContextPath() + "/groupwork-table?update=1");
	            break;
	        }
            default:
                throw new IllegalArgumentException("Unexpected value: " + path);
        }
    }
}