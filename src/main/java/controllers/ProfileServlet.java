package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TaskDAO;
import dto.TaskDTO;
import models.User;

@WebServlet(name = "profileController", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {
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

        // Lấy thông tin user đang đăng nhập từ Session
        User currentUser = (User) session.getAttribute("currentUser");
        
        // Gọi DAO lấy danh sách công việc của user này
        List<TaskDTO> tasks = taskDAO.getTasksByUserId(currentUser.getId());
        
        // Tính toán % tiến độ
        int totalTasks = tasks.size();
        int notStarted = 0;
        int inProgress = 0;
        int completed = 0;
        
        for (TaskDTO task : tasks) {
            String status = task.getStatusName().toLowerCase();
            if (status.contains("chua") || status.contains("chưa")) {
                notStarted++;
            } else if (status.contains("dang") || status.contains("đang")) {
                inProgress++;
            } else if (status.contains("hoan") || status.contains("hoàn") || status.contains("xong")) {
                completed++;
            }
        }
        
        int notStartedPct = totalTasks == 0 ? 0 : (notStarted * 100) / totalTasks;
        int inProgressPct = totalTasks == 0 ? 0 : (inProgress * 100) / totalTasks;
        int completedPct = totalTasks == 0 ? 0 : (completed * 100) / totalTasks;
        
        // Đẩy dữ liệu ra view
        req.setAttribute("tasks", tasks);
        req.setAttribute("notStartedPct", notStartedPct);
        req.setAttribute("inProgressPct", inProgressPct);
        req.setAttribute("completedPct", completedPct);
        
        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }
}