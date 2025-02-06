import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Epic extends Task {

    private final List<Integer> subTaskIds;
    
    public Epic(int id, String name, String description, TaskStatus status, TaskType type) {
        super(id, name, description, status, type);
        this.subTaskIds = new ArrayList<Integer>();
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void addSubTask(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    public void removeSubTask(int subTaskId) {
        subTaskIds.remove(subTaskId);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Integer> iterator = subTaskIds.iterator();
        while (iterator.hasNext()) {
            int subTaskId = iterator.next();
            stringBuilder.append(subTaskId);
            if (iterator.hasNext()) {
                stringBuilder.append("/");
            }
        }
        return super.toString() + "," + stringBuilder.toString();
    }
}
