public class Main {
    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();


        manager.createTask("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);

        manager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        manager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 2);
        manager.createSubTask("Поклеить обои", "Обои красивые", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 2);
        manager.createSubTask("Сделать потолок", "Потолок натяжной", TaskStatus.IN_PROGRESS, TaskType.SUBTASK_TYPE, 2);

        manager.createEpic("Построить дом", "Дом должен быть большим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        manager.createSubTask("Сделать фундамент", "Фундамент из бетона", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 6);
        manager.createSubTask("Сделать стены", "Стены из досок", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 6);
        manager.createSubTask("Сделать крышу", "Крыша из металла", TaskStatus.IN_PROGRESS, TaskType.SUBTASK_TYPE, 6);

        manager.createEpic("Съездить в отпуск", "Отпуск", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        manager.createSubTask("Купить билеты", "Билеты на поезд", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 10);
        manager.createSubTask("Забронировать отель", "Отель 5 звезд", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 10);
        manager.createSubTask("Собрать вещи", "Ничего не забыть", TaskStatus.IN_PROGRESS, TaskType.SUBTASK_TYPE, 10);

//        System.out.println(manager.getTaskById(1));
//
//        System.out.println(manager.getEpicById(2));
//        System.out.println(manager.getSubTaskById(3));
//        System.out.println(manager.getSubTaskById(4));
//        System.out.println(manager.getSubTaskById(5));
//
//        System.out.println(manager.getEpicById(6));
//        System.out.println(manager.getSubTaskById(7));
//        System.out.println(manager.getSubTaskById(8));
//        System.out.println(manager.getSubTaskById(9));
//
//        System.out.println(manager.getEpicById(10));
//        System.out.println(manager.getSubTaskById(11));
//        System.out.println(manager.getSubTaskById(12));
//        System.out.println(manager.getSubTaskById(13));
//
//        System.out.println(manager.getHistory());

    }
}
