import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private final List<Integer> subTaskIds;
    
    public Epic(int id, String name, String description, TaskStatus status) {
        super(id, name, description, status);
        this.subTaskIds = new ArrayList<Integer>();
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void addSubTask(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    @Override
    public String toString() {
        return super.toString() + ", subTaskIds=" + subTaskIds;
    }



}
