package model;

import enums.TaskType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Epic extends Task {

    private final List<Integer> subTaskIds;
    
    public Epic(String name, String description) {
        super(name, description);
        super.setType(TaskType.EPIC_TYPE);
        this.subTaskIds = new ArrayList<Integer>();
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void addSubTask(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    public void removeSubTask(int subTaskId) {
        subTaskIds.remove(Integer.valueOf(subTaskId));
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
        if (!stringBuilder.toString().isEmpty()) {
            return super.toString() + "," + stringBuilder.toString();
        } else {
            return super.toString();
        }

    }
}
