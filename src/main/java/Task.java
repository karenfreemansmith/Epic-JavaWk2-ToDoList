import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Task {
  private String description;
  private boolean completed;
  private LocalDateTime createdAt;
  private int id;

  public Task(String description) {
      this.description = description;
      this.completed = false;
      this.createdAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object otherTask) {
    if(!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId();
    }
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return completed;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void Save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks (description) VALUES (:description)";
      this.id = (int) con.createQuery(sql)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Task> all() {
    String sql = "SELECT id, description FROM taks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql). executeAndFetch(Task.class);
    }
  }

  public int getId() {
    return id;
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks were id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }
}
