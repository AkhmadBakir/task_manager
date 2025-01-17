public class Main {
    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();


        manager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS);

        manager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, 1);

        manager.createSubTask("Поклеить обои", "Обои красивые", TaskStatus.DONE, 1);

        manager.createSubTask("Сделать потолок", "Потолок натяжной", TaskStatus.IN_PROGRESS, 1);


        manager.createEpic("Построить дом", "Дом должен быть большим", TaskStatus.IN_PROGRESS);

        manager.createSubTask("Сделать фундамент", "Фундамент из бетона", TaskStatus.DONE, 2);

        manager.createSubTask("Сделать стены", "Стены из досок", TaskStatus.DONE, 2);

        manager.createSubTask("Сделать крышу", "Крыша из металла", TaskStatus.IN_PROGRESS, 2);


        System.out.println(manager.getSubTaskById(1));
        System.out.println(manager.getSubTaskById(2));
        System.out.println(manager.getSubTaskById(3));
        System.out.println(manager.getSubTaskById(4));
        System.out.println(manager.getSubTaskById(5));
        System.out.println(manager.getSubTaskById(6));

        System.out.println(manager.subTask.toString());

        manager.deleteEpicById(2);

        System.out.println(manager.subTask.toString());


    }
}
