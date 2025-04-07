import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static enums.TaskStatus.DONE;
import static enums.TaskStatus.NEW;
import static enums.TaskType.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class InMemoryTaskManagerTest {

    service.impl.InMemoryTaskManager inMemoryTaskManager = new service.impl.InMemoryTaskManager();

    @BeforeEach
    void init() {
        inMemoryTaskManager.getTask().clear();
        inMemoryTaskManager.getEpic().clear();
        inMemoryTaskManager.getSubTask().clear();
    }

    @Test
    void shouldCreateTask() {
        inMemoryTaskManager.createTask("Сходить в кино", "Купить билеты");
        assertThat(inMemoryTaskManager.getTask().size()).isEqualTo(1);
        assertThat(TASK_TYPE).isEqualTo(inMemoryTaskManager.getTaskById(1).getType());
    }

    @Test
    void shouldCreateEpic() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        assertThat(inMemoryTaskManager.getEpicById(1).getId()).isEqualTo(1);
        assertThat(inMemoryTaskManager.getEpicById(1).getName()).isEqualTo("Сделать ремонт");
        assertThat(inMemoryTaskManager.getEpicById(1).getDescription()).isEqualTo("Ремонт должен быть хорошим");
        assertThat(inMemoryTaskManager.getEpicById(1).getStatus()).isEqualTo(NEW);
        assertThat(inMemoryTaskManager.getEpicById(1).getType()).isEqualTo(EPIC_TYPE);
    }

    @Test
    void shouldCreateSubTask() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", 1);

        assertThat(inMemoryTaskManager.getSubTaskById(2).getId()).isEqualTo(2);
        assertThat(inMemoryTaskManager.getSubTaskById(2).getName()).isEqualTo("Сделать полы");
        assertThat(inMemoryTaskManager.getSubTaskById(2).getDescription()).isEqualTo("Полы из ламината");
        assertThat(inMemoryTaskManager.getSubTaskById(2).getStatus()).isEqualTo(NEW);
        assertThat(inMemoryTaskManager.getSubTaskById(2).getType()).isEqualTo(SUBTASK_TYPE);
        assertThat(inMemoryTaskManager.getSubTaskById(2).getEpicId()).isEqualTo(1);
    }

    @Test
    void shouldDeleteAllTasks() {
        inMemoryTaskManager.createTask("Сходить в кино", "Купить билеты");
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", 2);
        inMemoryTaskManager.deleteAllTasks();

        assertThat(inMemoryTaskManager.getTaskById(1)).isEqualTo(null);
        assertThat(inMemoryTaskManager.getEpicById(2)).isEqualTo(null);
        assertThat(inMemoryTaskManager.getSubTaskById(3)).isEqualTo(null);
    }

    @Test
    void shouldDeleteTaskById() {
        inMemoryTaskManager.createTask("Сходить в кино", "Купить билеты");
        inMemoryTaskManager.deleteTaskById(1);

        assertThat(inMemoryTaskManager.getSubTaskById(1)).isEqualTo(null);
    }

    @Test
    void shouldDeleteEpicById() {
        inMemoryTaskManager.createEpic("Сходить в кино", "Купить билеты");
        inMemoryTaskManager.deleteEpicById(1);

        assertThat(inMemoryTaskManager.getEpicById(1)).isEqualTo(null);
    }

    @Test
    void shouldDeleteSubTaskById() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", 1);
        inMemoryTaskManager.deleteSubTaskById(2);

        assertThat(inMemoryTaskManager.getSubTaskById(2)).isEqualTo(null);
    }

    @Test
    void shouldUpdateTaskById() {
        inMemoryTaskManager.createTask("Сходить в кино", "Купить билеты");
        assertThat(inMemoryTaskManager.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", NEW, TASK_TYPE);
        Task task = new Task("Сходил в кино", "Купил билеты");
        task.setId(1);
        task.setStatus(DONE);
        inMemoryTaskManager.updateTaskById(task);
        assertThat(inMemoryTaskManager.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходил в кино", "Купил билеты", DONE, TASK_TYPE);
    }

    @Test
    void shouldUpdateEpicById() {
        inMemoryTaskManager.createEpic("Сходить в кино", "Купить билеты");
        assertThat(inMemoryTaskManager.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходить в кино", "Купить билеты", NEW, EPIC_TYPE);
        Epic epic = new Epic("Сходил в кино", "Купил билеты");
        epic.setId(1);
        epic.setStatus(DONE);
        inMemoryTaskManager.updateEpicById(epic);
        assertThat(inMemoryTaskManager.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходил в кино", "Купил билеты", DONE, EPIC_TYPE);
    }

    @Test
    void shouldUpdateSubTaskById() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", 1);
        assertThat(inMemoryTaskManager.getSubTaskById(2))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", NEW, SUBTASK_TYPE, 1);
        SubTask subTask = new SubTask("Сделал полы", "Полы из досок", 1);
        subTask.setId(2);
        subTask.setStatus(DONE);
        inMemoryTaskManager.updateSubTaskById(subTask);
        assertThat(inMemoryTaskManager.getSubTaskById(2))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделал полы", "Полы из досок",DONE,SUBTASK_TYPE,1);
    }

    @Test
    void shouldGetTaskById() {
        inMemoryTaskManager.createTask("Сходить в кино", "Купить билеты");
        Task testTask = inMemoryTaskManager.getTaskById(1);
        assertThat(testTask)
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", NEW, TASK_TYPE);
    }

    @Test
    void shouldGetEpicById() {
        inMemoryTaskManager.createEpic("Сходить в кино", "Купить билеты");
        Epic testEpic = inMemoryTaskManager.getEpicById(1);
        assertThat(testEpic)
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходить в кино", "Купить билеты", NEW, EPIC_TYPE);
    }

    @Test
    void shouldGetSubTaskById() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", 1);
        assertThat(inMemoryTaskManager.getSubTaskById(2))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", NEW, SUBTASK_TYPE, 1);
    }

    @Test
    void shouldGetSubTasksInEpic() {
        inMemoryTaskManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        inMemoryTaskManager.createSubTask("Сделать полы", "Полы из ламината", 1);
        inMemoryTaskManager.createSubTask("Поклеить обои", "Обои красивые", 1);
        assertThat(inMemoryTaskManager.getEpic().get(1).getSubTaskIds())
                .containsExactlyInAnyOrder(2, 3);
    }

}