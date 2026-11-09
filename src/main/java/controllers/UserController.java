package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoleDAO;
import dao.TaskDAO;
import dto.CreateUserDTO;
import dto.TaskDTO;
import models.User;
import services.UserService;

@WebServlet(name = "userController", urlPatterns = { 
    "/user-table", 
    "/user-add", 
    "/user-edit", 
    "/user-delete",
    "/user-details"
})
public class UserController extends HttpServlet {
    private final UserService userService;

    public UserController() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();
        switch (path) {
            case "/user-table": {
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

                try {
                    int totalRow = userService.countUsers(keywords);
                    int totalPage = (totalRow + size - 1) / size;

                    req.setAttribute("totalPage", totalPage);
                    req.setAttribute("page", page);
                    req.setAttribute("users", userService.getUserPaging(keywords, size, page));
                    req.getRequestDispatcher("/views/user-table.jsp").forward(req, resp);
                } catch (SQLException e) {
                    throw new ServletException("Khong tai duoc danh sach User", e);
                }
                break;
            }
            case "/user-add": {
                req.setAttribute("roles", new RoleDAO().getAll());
                req.getRequestDispatcher("/views/user-add.jsp").forward(req, resp);
                break;
            }
            case "/user-edit": {
                int id;
                try {
                    id = Integer.parseInt(req.getParameter("id"));
                } catch (Exception e) {
                    return;
                }
                try {
                    User user = userService.findById(id);
                    req.setAttribute("userEdit", user);
                } catch (Exception e) {
                    return;
                }
                
                req.setAttribute("roles", new RoleDAO().getAll());
                req.setAttribute("isEdit", true);
                req.getRequestDispatcher("/views/user-add.jsp").forward(req, resp);
                break;
            }
            case "/user-details": {
                int id;
                try {
                    id = Integer.parseInt(req.getParameter("id"));
                } catch (Exception e) {
                    resp.sendRedirect(req.getContextPath() + "/user-table");
                    return;
                }
                try {
                    User user = userService.findById(id);
                    req.setAttribute("userDetail", user);
                    
                    TaskDAO taskDAO = new TaskDAO();
                    List<TaskDTO> tasks = taskDAO.getTasksByEmployee(id);
                    
                    int totalTasks = tasks.size();
                    int notStarted = 0;
                    int inProgress = 0;
                    int completed = 0;
                    
                    List<TaskDTO> notStartedTasks = new ArrayList<>();
                    List<TaskDTO> inProgressTasks = new ArrayList<>();
                    List<TaskDTO> completedTasks = new ArrayList<>();
                    
                    for (TaskDTO task : tasks) {
                        String status = task.getStatusName().toLowerCase();
                        if (status.contains("chua") || status.contains("chưa")) {
                            notStarted++;
                            notStartedTasks.add(task);
                        } else if (status.contains("dang") || status.contains("đang")) {
                            inProgress++;
                            inProgressTasks.add(task);
                        } else if (status.contains("hoan") || status.contains("hoàn") || status.contains("xong")) {
                            completed++;
                            completedTasks.add(task);
                        }
                    }
                    
                    req.setAttribute("notStartedPct", totalTasks == 0 ? 0 : (notStarted * 100) / totalTasks);
                    req.setAttribute("inProgressPct", totalTasks == 0 ? 0 : (inProgress * 100) / totalTasks);
                    req.setAttribute("completedPct", totalTasks == 0 ? 0 : (completed * 100) / totalTasks);
                    
                    req.setAttribute("notStartedTasks", notStartedTasks);
                    req.setAttribute("inProgressTasks", inProgressTasks);
                    req.setAttribute("completedTasks", completedTasks);
                    
                    req.getRequestDispatcher("/views/user-details.jsp").forward(req, resp);
                } catch (Exception e) {
                    resp.sendRedirect(req.getContextPath() + "/user-table");
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/user-add": {
                CreateUserDTO dto = new CreateUserDTO();
                dto.setEmail(req.getParameter("email"));
                dto.setPassword(req.getParameter("password"));
                dto.setFullname(req.getParameter("fullname"));
                dto.setPhone(req.getParameter("phone"));
                dto.setCountry(req.getParameter("country"));
                dto.setRoleId(Integer.parseInt(req.getParameter("roleId")));

                String error = userService.create(dto);
                if (error == null) {
                    req.setAttribute("error", null);
                    resp.sendRedirect(req.getContextPath() + "/user-table?add=1");
                    return;
                }
                req.setAttribute("error", error);
                req.setAttribute("roles", new RoleDAO().getAll());
                req.getRequestDispatcher("/views/user-add.jsp").forward(req, resp);
                return;
            }
            case "/user-delete": {
                int id;
                try {
                    id = Integer.parseInt(req.getParameter("id"));
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                
                models.User currentUser = (models.User) req.getSession().getAttribute("currentUser");
                if (currentUser != null && currentUser.getId() == id) {
                    resp.sendRedirect(req.getContextPath() + "/user-table?err=2");
                    return;
                }

                try {
                    String err = userService.deleteById(id);
                    if (err == null) {
                        resp.sendRedirect(req.getContextPath() + "/user-table?delete=1");
                    } else {
                        resp.sendRedirect(req.getContextPath() + "/user-table?err=1");
                    }
                } catch (Exception e) {
                }
                return;
            }
            case "/user-edit": {
                req.setCharacterEncoding("UTF-8");
                int roleId;
                int id;
                try {
                    roleId = Integer.parseInt(req.getParameter("roleId"));
                    id = Integer.parseInt(req.getParameter("id"));
                } catch (Exception e) {
                    req.setAttribute("error", "RoleId hoac Id sai dinh dang");
                    return;
                }
                User user = new User();
                user.setFullname(req.getParameter("fullname"));
                user.setEmail(req.getParameter("email"));
                user.setPhone(req.getParameter("phone"));
                user.setCountry(req.getParameter("country"));
                user.setRoleId(roleId);
                user.setId(id);
                
                try {
                    String err = userService.update(user);
                    if (err == null) {
                        resp.sendRedirect(req.getContextPath() + "/user-table?update=1");
                        return;
                    }
                    req.setAttribute("error", err);
                    req.setAttribute("roles", new dao.RoleDAO().getAll());
                    req.setAttribute("userEdit", user);
                    req.setAttribute("isEdit", true);
                    req.getRequestDispatcher("/views/user-add.jsp").forward(req, resp);
                } catch (SQLException e) {
                    req.setAttribute("error", "Lỗi CSDL");
                    req.setAttribute("roles", new dao.RoleDAO().getAll());
                    req.setAttribute("userEdit", user);
                    req.setAttribute("isEdit", true);
                    req.getRequestDispatcher("/views/user-add.jsp").forward(req, resp);
                }
                return;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + path);
        }
    }
}