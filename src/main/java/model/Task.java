package model;

import enums.TaskStatus;
import enums.TaskType;

public class Task {

    private Integer id;
    private String name;
    private String description;
    private TaskStatus status;
    private TaskType type;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.type = TaskType.TASK_TYPE;
    }

    public Task() {};

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public TaskStatus getStatus() { return status; }

    public void setStatus(TaskStatus status) { this.status = status; }

    public TaskType getType() { return this.type; }

    public void setType(TaskType type) { this.type = type; }

    public String toString() {
        return id + "," + name + "," + description + "," + status + "," + type;
    }

}
