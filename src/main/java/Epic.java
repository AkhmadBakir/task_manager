import java.util.Map;

public class Epic extends Task {

    private Manager subTask;

//    public Epic(Integer id, String name, String description, TaskStatus status, SubTask subTask) {
//        super(id, name, description, status);
//        this.subTask = subTask;
//    }

    public Epic(int id, String name, String description, TaskStatus status, Manager subTask) {
        super(id, name, description, status);
        this.subTask = subTask;
    }

    public Manager getSubTask() {
        return subTask;
    }

    public void setSubTask(Manager subTask) {
        this.subTask = subTask;
    }

    //    @Override
//    public String toString() {
//        return "Epic{" +
//                "subTask=" + subTask +
//                '}';
//    }

//    public String toString() {
//        return "Task{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", status='" + status + '\'' +
//                '}';


}
