public class Task {

    private Integer id;
    private String name;
    private String description;
    private TaskStatus status;
    private TaskType type;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskType getType() { return this.type; }

    public void setType(TaskType type) { this.type = type; }

    public Task(int id, String name, String description, TaskStatus status, TaskType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public String toString() {
        return id + "," + name + "," + description + "," + status + "," + type;
    }

}
