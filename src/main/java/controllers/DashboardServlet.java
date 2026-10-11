package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TaskDAO;

@WebServlet(name = "dashboardController", urlPatterns = {"/index", ""})
public class DashboardServlet extends HttpServlet {
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

        int[] stats = taskDAO.getTaskStatistics();
        int notStarted = stats[0];
        int inProgress = stats[1];
        int completed = stats[2];
        int totalTasks = notStarted + inProgress + completed;

        req.setAttribute("notStarted", notStarted);
        req.setAttribute("inProgress", inProgress);
        req.setAttribute("completed", completed);
        
        req.setAttribute("notStartedPct", totalTasks == 0 ? 0 : (notStarted * 100) / totalTasks);
        req.setAttribute("inProgressPct", totalTasks == 0 ? 0 : (inProgress * 100) / totalTasks);
        req.setAttribute("completedPct", totalTasks == 0 ? 0 : (completed * 100) / totalTasks);

        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }
}