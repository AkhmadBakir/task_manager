import java.util.Map;

public class SubTask extends Task {
    private int epicId;

    public SubTask(Integer id, String name, String description, TaskStatus status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return super.toString() + ", epicId=" + epicId;
    }
}
