package dto;

import java.sql.Date;

public class TaskDTO {
    private int id;
    private String taskName;
    private String projectName;
    private String userName;
    private Date startDate;
    private Date endDate;
    private String statusName;

    public TaskDTO() {}

    public TaskDTO(int id, String taskName, String projectName, String userName, Date startDate, Date endDate, String statusName) {
        this.id = id;
        this.taskName = taskName;
        this.projectName = projectName;
        this.userName = userName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusName = statusName;
    }

    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getTaskName() { 
        return taskName; 
    }
    
    public void setTaskName(String taskName) { 
        this.taskName = taskName; 
    }
    
    public String getProjectName() { 
        return projectName; 
    }
    
    public void setProjectName(String projectName) { 
        this.projectName = projectName; 
    }
    
    public String getUserName() { 
        return userName; 
    }
    
    public void setUserName(String userName) { 
        this.userName = userName; 
    }
    
    public Date getStartDate() { 
        return startDate; 
    }
    
    public void setStartDate(Date startDate) { 
        this.startDate = startDate; 
    }
    
    public Date getEndDate() { 
        return endDate; 
    }
    
    public void setEndDate(Date endDate) { 
        this.endDate = endDate; 
    }
    
    public String getStatusName() { 
        return statusName; 
    }
    
    public void setStatusName(String statusName) { 
        this.statusName = statusName; 
    }
}