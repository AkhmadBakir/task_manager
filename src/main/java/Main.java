
public class Main {
    public static void main(String[] args) {

        Manager task1 = new Manager();

        task1.createTask("Сделать домашку", "Сделать всю домашку до воскресенья", TaskStatus.NEW);

        System.out.println(task1.getTaskById(1));

        task1.updateTaskById(1, "Сделать домашку", "Сделать всю домашку до воскресенья", TaskStatus.DONE);

        System.out.println(task1.getTaskById(1));

        Manager task2 = new Manager();

        task2.createTask("Сходить в магазин", "Купить еду", TaskStatus.NEW);

        System.out.println(task2.getTaskById(1).toString());

        task2.createTask("Сходить в кино", "Купить билеты", TaskStatus.NEW);

        System.out.println(task2.getTask());

        task2.deleteTaskById(2);

        System.out.println(task2.getTask());

        task2.updateTaskById(1, "Сходил в кино", "Купил билеты", TaskStatus.DONE);

        System.out.println(task2.getTask());

        Manager subTasksHouseBuilder = new Manager();

        subTasksHouseBuilder.createSubTask("Сделать фундамент", "Фундамент из бетона", TaskStatus.IN_PROGRESS);

        subTasksHouseBuilder.addToSubTask("Сделать стены", "Стены из досок", TaskStatus.IN_PROGRESS);

        subTasksHouseBuilder.addToSubTask("Сделать крышу", "Крыша из металла", TaskStatus.IN_PROGRESS);

        Manager epicHouseBuilder = new Manager();

        epicHouseBuilder.createEpic("Построить дом", "Дом должен быть большим", TaskStatus.IN_PROGRESS, subTasksHouseBuilder);

        System.out.println(epicHouseBuilder.getEpic());

        System.out.println(epicHouseBuilder.getEpicById(1));

        System.out.println(epicHouseBuilder.getSubTasksInEpic(1)); //Выдает ссылку на объект класса Manager

        subTasksHouseBuilder.updateSubTaskById(1,"Сделать фундамент", "Фундамент из бетона", TaskStatus.DONE);

        epicHouseBuilder.deleteAllTasks();

        System.out.println(epicHouseBuilder.getEpic());


    }
}
