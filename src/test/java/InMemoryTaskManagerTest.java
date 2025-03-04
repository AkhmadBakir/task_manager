import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class InMemoryTaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @BeforeEach
    void init() {
        inMemoryTaskManager.task.clear();
        inMemoryTaskManager.epic.clear();
        inMemoryTaskManager.subTask.clear();
    }

    @Test
    void shouldCreateTask() {
        inMemoryTaskManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        assertThat(inMemoryTaskManager.task.size()).isEqualTo(1);
        assertThat(TaskType.TASK_TYPE).isEqualTo(inMemoryTaskManager.getTaskById(1).getType());
    }

    @Test
    void shouldCreateEpic() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        assertThat(inMemoryTaskManager.getEpicById(1).getId()).isEqualTo(1);
        assertThat(inMemoryTaskManager.getEpicById(1).getName()).isEqualTo("Сделать ремонт");
        assertThat(inMemoryTaskManager.getEpicById(1).getDescription()).isEqualTo("Ремонт должен быть хорошим");
        assertThat(inMemoryTaskManager.getEpicById(1).getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(inMemoryTaskManager.getEpicById(1).getType()).isEqualTo(TaskType.EPIC_TYPE);
    }

    @Test
    void shouldCreateSubTask() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);

        assertThat(inMemoryTaskManager.getSubTaskById(2).getId()).isEqualTo(2);
        assertThat(inMemoryTaskManager.getSubTaskById(2).getName()).isEqualTo("Сделать полы");
        assertThat(inMemoryTaskManager.getSubTaskById(2).getDescription()).isEqualTo("Полы из ламината");
        assertThat(inMemoryTaskManager.getSubTaskById(2).getStatus()).isEqualTo(TaskStatus.DONE);
        assertThat(inMemoryTaskManager.getSubTaskById(2).getType()).isEqualTo(TaskType.SUBTASK_TYPE);
        assertThat(inMemoryTaskManager.getSubTaskById(2).getEpicId()).isEqualTo(1);
    }

    @Test
    void shouldDeleteAllTasks() {
        inMemoryTaskManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 2);
        inMemoryTaskManager.deleteAllTasks();

        assertThat(inMemoryTaskManager.getTaskById(1)).isEqualTo(null);
        assertThat(inMemoryTaskManager.getEpicById(2)).isEqualTo(null);
        assertThat(inMemoryTaskManager.getSubTaskById(3)).isEqualTo(null);
    }

    @Test
    void shouldDeleteTaskById() {
        inMemoryTaskManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        inMemoryTaskManager.deleteTaskById(1);

        assertThat(inMemoryTaskManager.getSubTaskById(1)).isEqualTo(null);
    }

    @Test
    void shouldDeleteEpicById() {
        inMemoryTaskManager.createEpic("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        inMemoryTaskManager.deleteEpicById(1);

        assertThat(inMemoryTaskManager.getEpicById(1)).isEqualTo(null);
    }

    @Test
    void shouldDeleteSubTaskById() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
        inMemoryTaskManager.deleteSubTaskById(2);

        assertThat(inMemoryTaskManager.getSubTaskById(2)).isEqualTo(null);
    }

    @Test
    void shouldUpdateTaskById() {
        inMemoryTaskManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.TASK_TYPE);
        Task testTask = inMemoryTaskManager.getTaskById(1);
        assertThat(testTask)
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.TASK_TYPE);
        inMemoryTaskManager.updateTaskById(1, "Сходил в кино", "Купил билеты", TaskStatus.DONE);
        assertThat(testTask)
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходил в кино", "Купил билеты", TaskStatus.DONE, TaskType.TASK_TYPE);
    }

    @Test
    void shouldUpdateEpicById() {
        inMemoryTaskManager.createEpic("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.EPIC_TYPE);
        Epic testEpic = inMemoryTaskManager.getEpicById(1);
        assertThat(testEpic)
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.EPIC_TYPE);
        inMemoryTaskManager.updateEpicById(1, "Сходил в кино", "Купил билеты", TaskStatus.DONE);
        assertThat(testEpic)
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходил в кино", "Купил билеты", TaskStatus.DONE, TaskType.EPIC_TYPE);
    }

    @Test
    void shouldUpdateSubTaskById() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.NEW, TaskType.SUBTASK_TYPE, 1);
        SubTask testSubTask = inMemoryTaskManager.getSubTaskById(2);
        assertThat(testSubTask)
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", TaskStatus.NEW, TaskType.SUBTASK_TYPE, 1);
        inMemoryTaskManager.updateSubTaskById(2, "Сделал полы", "Полы из досок", TaskStatus.DONE);
        assertThat(testSubTask)
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделал полы", "Полы из досок", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
    }

    @Test
    void shouldGetTaskById() {
        inMemoryTaskManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.TASK_TYPE);
        Task testTask = inMemoryTaskManager.getTaskById(1);
        assertThat(testTask)
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.TASK_TYPE);
    }

    @Test
    void shouldGetEpicById() {
        inMemoryTaskManager.createEpic("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.EPIC_TYPE);
        Epic testEpic = inMemoryTaskManager.getEpicById(1);
        assertThat(testEpic)
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.EPIC_TYPE);
    }

    @Test
    void shouldGetSubTaskById() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
        SubTask testSubTask = inMemoryTaskManager.getSubTaskById(2);
        assertThat(testSubTask)
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
    }

    @Test
    void shouldGetSubTasksInEpic() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
        inMemoryTaskManager.createSubTask("Поклеить обои", "Обои красивые", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
        assertThat(inMemoryTaskManager.epic.get(1).getSubTaskIds())
                .containsExactlyInAnyOrder(2, 3);
    }

}