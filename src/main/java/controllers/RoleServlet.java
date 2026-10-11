package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RoleDAO;
import models.Role;

@WebServlet(name = "roleController", urlPatterns = {"/role-table", "/role-add", "/role-edit", "/role-delete"})
public class RoleServlet extends HttpServlet {
    private RoleDAO roleDAO;
    
    @Override
    public void init() throws ServletException {
        roleDAO = new RoleDAO();
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
            case "/role-table": {
                List<Role> list = roleDAO.getAll();
                req.setAttribute("list", list);
                req.getRequestDispatcher("/views/role-table.jsp").forward(req, resp);
                break;
            }
            case "/role-add": {
                req.getRequestDispatcher("/views/role-add.jsp").forward(req, resp);
                break;
            }
            case "/role-edit": {
                int id = Integer.parseInt(req.getParameter("id"));
                Role role = roleDAO.findById(id);
                req.setAttribute("role", role);
                req.getRequestDispatcher("/views/role-edit.jsp").forward(req, resp);
                break;
            }
            case "/role-delete": {
                int id = Integer.parseInt(req.getParameter("id"));
                roleDAO.deleteById(id);
                resp.sendRedirect(req.getContextPath() + "/role-table");
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
            case "/role-add": {
                String name = req.getParameter("name");
                String description = req.getParameter("description");
                
                roleDAO.insert(name, description);
                
                resp.sendRedirect(req.getContextPath() + "/role-table");
                break;
            }
            case "/role-edit": {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                String description = req.getParameter("description");
                
                roleDAO.update(id, name, description);
                
                resp.sendRedirect(req.getContextPath() + "/role-table");
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + path);
        }
    }
}