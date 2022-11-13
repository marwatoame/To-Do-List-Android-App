package bzu.androidstudioproject.Tasks;

import java.util.Date;

public class Task {

    private int Id;
    private String title;
    private String Description;
    private Date time;
    private String Email;
    private boolean status;


    public Task(int Id, String title, String description, Date time, String email) {
        this.Id = Id;
        this.title = title;
        Description = description;
        this.time = time;
        Email = email;
        status = false;
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isStatus() {
        return status;
    }
    public boolean getStatus()
    {
        return this.status;
    }
    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
