public class Main {
    public static void main(String[] args) {

        Manager manager = new Manager();

        manager.createTask("Сделать домашку", "Сделать всю домашку до воскресенья", TaskStatus.NEW);

        System.out.println(manager.getTasksById(1));

        manager.updateTasksById(1, "Сделать домашку", "Сделать всю домашку до воскресенья", TaskStatus.DONE);

        System.out.println(manager.task.toString());

        manager.createTask("Сходить в магазин", "Купить еду", TaskStatus.NEW);

        System.out.println(manager.task.toString());

        System.out.println(manager.getTasksById(2));

        manager.deleteTaskById(2);

        System.out.println(manager.task.toString());


    }
}
