
public class Main {
    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();

        manager.createTask("Сделать домашку", "Сделать всю домашку до воскресенья", TaskStatus.NEW);

        System.out.println(manager.getTaskById(1));

        manager.updateTaskById(1, "Сделать домашку", "Сделать всю домашку до воскресенья", TaskStatus.DONE);

        System.out.println(manager.getTaskById(1));

        manager.createTask("Сходить в магазин", "Купить еду", TaskStatus.NEW);

       System.out.println(manager.getTaskById(2));

        manager.createTask("Сходить в кино", "Купить билеты", TaskStatus.NEW);

        manager.updateTaskById(3, "Сходил в кино", "Купил билеты", TaskStatus.DONE);

        System.out.println(manager.getTaskById(3));

        manager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS);

        manager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, 1);

        manager.createEpic("Построить дом", "Дом должен быть большим", TaskStatus.IN_PROGRESS);

        manager.createSubTask("Сделать фундамент", "Фундамент из бетона", TaskStatus.DONE, 2);

        manager.createSubTask("Поклеить обои", "Обои красивые", TaskStatus.DONE, 1);

        manager.createSubTask("Сделать стены", "Стены из досок", TaskStatus.DONE, 2);

        manager.createSubTask("Сделать потолок", "Потолок натяжной", TaskStatus.IN_PROGRESS, 1);

        manager.createSubTask("Сделать крышу", "Крыша из металла", TaskStatus.IN_PROGRESS, 2);

        System.out.println(manager.getSubTaskById(3));

        System.out.println(manager.getEpicById(1));

        System.out.println(manager.getSubTasksInEpic(1));

        System.out.println(manager.getEpicById(1));

        System.out.println(manager.getHistory().size());


    }
}
