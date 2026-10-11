package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProjectDAO;
import models.Project;

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
        if(session == null || session.getAttribute("currentUser") == null) {
            resp.sendRedirect(req.getContextPath() +"/login");
            return;
        }

        String path = req.getServletPath();
        switch (path) {
            case "/groupwork-table": {
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
                
                int totalRow = projectDAO.countProjects(keywords);
                int totalPage = (totalRow + size - 1) / size;
                
                req.setAttribute("totalPage", totalPage);
                req.setAttribute("page", page);
                req.setAttribute("list", projectDAO.getProjectPaging(keywords, size, page));
                
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
                projectDAO.deleteById(id);
                resp.sendRedirect(req.getContextPath() + "/groupwork-table");
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
                Date startDate = Date.valueOf(req.getParameter("startDate"));
                Date endDate = Date.valueOf(req.getParameter("endDate"));

                projectDAO.insert(name, startDate, endDate);

                resp.sendRedirect(req.getContextPath() + "/groupwork-table");
                break;
            }
            case "/groupwork-edit": {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("projectName");
                Date startDate = Date.valueOf(req.getParameter("startDate"));
                Date endDate = Date.valueOf(req.getParameter("endDate"));

                projectDAO.update(id, name, startDate, endDate);

                resp.sendRedirect(req.getContextPath() + "/groupwork-table");
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + path);
        }
    }
}