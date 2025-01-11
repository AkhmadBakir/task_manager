
public class Main {
    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();

//        manager.createTask("Сделать домашку", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
//
//        System.out.println(manager.getTaskById(1));
//
//        manager.updateTaskById(1, "Сделать домашку", "Сделать всю домашку до воскресенья", TaskStatus.DONE);
//
//        System.out.println(manager.getTaskById(1));
//
//        manager.createTask("Сходить в магазин", "Купить еду", TaskStatus.NEW);
//
//        System.out.println(manager.getTaskById(2));
//
//        manager.createTask("Сходить в кино", "Купить билеты", TaskStatus.NEW);
//
//        manager.updateTaskById(3, "Сходил в кино", "Купил билеты", TaskStatus.DONE);
//
//        System.out.println(manager.getTaskById(3));
//
//        manager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS);
//
//        manager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, 1);
//
//        manager.createEpic("Построить дом", "Дом должен быть большим", TaskStatus.IN_PROGRESS);
//
//        manager.createSubTask("Сделать фундамент", "Фундамент из бетона", TaskStatus.DONE, 2);
//
//        manager.createSubTask("Поклеить обои", "Обои красивые", TaskStatus.DONE, 1);
//
//        manager.createSubTask("Сделать стены", "Стены из досок", TaskStatus.DONE, 2);
//
//        manager.createSubTask("Сделать потолок", "Потолок натяжной", TaskStatus.IN_PROGRESS, 1);
//
//        manager.createSubTask("Сделать крышу", "Крыша из металла", TaskStatus.IN_PROGRESS, 2);
//
//        System.out.println(manager.getSubTaskById(3));
//
//        System.out.println(manager.getEpicById(1));
//
//        System.out.println(manager.getSubTasksInEpic(1));
//
//        System.out.println(manager.getEpicById(1));
//
//        System.out.println(manager.getEpicByIdWithSubTasks(1));


        manager.createTask("Сделать домашку1", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(1));
        manager.createTask("Сделать домашку2", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(2));
        manager.createTask("Сделать домашку3", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(3));
        manager.createTask("Сделать домашку4", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(4));
        manager.createTask("Сделать домашку5", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(5));
        manager.createTask("Сделать домашку6", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(6));
        manager.createTask("Сделать домашку7", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(7));
        manager.createTask("Сделать домашку8", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(8));
        manager.createTask("Сделать домашку9", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(9));
        manager.createTask("Сделать домашку10", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(10));
        manager.createTask("Сделать домашку11", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(11));
        manager.createTask("Сделать домашку12", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(12));
        manager.createTask("Сделать домашку13", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(13));
        manager.createTask("Сделать домашку14", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(14));
        manager.createTask("Сделать домашку15", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(15));
        manager.createTask("Сделать домашку16", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(16));
        manager.createTask("Сделать домашку17", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(17));
        manager.createTask("Сделать домашку18", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(18));
        manager.createTask("Сделать домашку19", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(19));
        manager.createTask("Сделать домашку20", "Сделать всю домашку до воскресенья", TaskStatus.NEW);
        System.out.println(manager.getTaskById(20));
        System.out.println(manager.getTaskById(1));
        System.out.println(manager.getTaskById(2));
        System.out.println(manager.getTaskById(3));
        System.out.println(manager.getTaskById(18));
        System.out.println(manager.getTaskById(19));
        System.out.println(manager.getTaskById(20));

        System.out.println(manager.getHistory());



    }
}
