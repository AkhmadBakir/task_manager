import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest {

    File file = new File("historyTestFileSave.csv");
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

    @Test
    void shouldFileBackedTasksManagerSaveInFile() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 2);
        fileBackedTasksManager.save();
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        assertThat(fileBackedTasksManager1.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        assertThat(fileBackedTasksManager1.getEpicById(2))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        assertThat(fileBackedTasksManager1.getSubTaskById(3))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 2);
    }

    @Test
    void shouldMakeTaskFromString() {
        String string = "1,Сходить в кино,Купить билеты,IN_PROGRESS,TASK_TYPE";
        Task testTask = FileBackedTasksManager.fromString(string);
        assertThat(testTask)
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
    }

    @Test
    void shouldRecordHistoryToString() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        historyManager.add(new Task(1,"Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE));
        historyManager.add(new Epic(2, "Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE));
        historyManager.add(new SubTask(3, "Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 2));
        String historyForString = FileBackedTasksManager.historyToString(historyManager);
        assertEquals("1,2,3", historyForString);
    }

    @Test
    void shouldLoadHistoryFromString() {
        String value = "1,2,3,4,5";
        List<Task> history = FileBackedTasksManager.historyFromString(value);
        assertThat(history.size()).isEqualTo(5);
    }

    @Test
    void shouldLoadFileBackedTasksManagerFromFile() {
        File file1 = new File("historyTest.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file1);
        assertThat(fileBackedTasksManager.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        assertThat(fileBackedTasksManager.getEpicById(2))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        assertThat(fileBackedTasksManager.getSubTaskById(3))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 2);
    }


    @BeforeEach
    void init() {
        fileBackedTasksManager.task.clear();
        fileBackedTasksManager.epic.clear();
        fileBackedTasksManager.subTask.clear();

        try(FileWriter fileWriter = new FileWriter(file, false)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldCreateTask() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);

        assertThat(fileBackedTasksManager.getTaskById(1).getId()).isEqualTo(1);
        assertThat(fileBackedTasksManager.getTaskById(1).getName()).isEqualTo("Сходить в кино");
        assertThat(fileBackedTasksManager.getTaskById(1).getDescription()).isEqualTo("Купить билеты");
        assertThat(fileBackedTasksManager.getTaskById(1).getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(fileBackedTasksManager.getTaskById(1).getType()).isEqualTo(TaskType.TASK_TYPE);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
    }

    @Test
    void shouldCreateEpic() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);

        assertThat(fileBackedTasksManager.getEpicById(1).getId()).isEqualTo(1);
        assertThat(fileBackedTasksManager.getEpicById(1).getName()).isEqualTo("Сделать ремонт");
        assertThat(fileBackedTasksManager.getEpicById(1).getDescription()).isEqualTo("Ремонт должен быть хорошим");
        assertThat(fileBackedTasksManager.getEpicById(1).getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(fileBackedTasksManager.getEpicById(1).getType()).isEqualTo(TaskType.EPIC_TYPE);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
    }

    @Test
    void shouldCreateSubTask() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);

        assertThat(fileBackedTasksManager.getSubTaskById(2).getId()).isEqualTo(2);
        assertThat(fileBackedTasksManager.getSubTaskById(2).getName()).isEqualTo("Сделать полы");
        assertThat(fileBackedTasksManager.getSubTaskById(2).getDescription()).isEqualTo("Полы из ламината");
        assertThat(fileBackedTasksManager.getSubTaskById(2).getStatus()).isEqualTo(TaskStatus.DONE);
        assertThat(fileBackedTasksManager.getSubTaskById(2).getType()).isEqualTo(TaskType.SUBTASK_TYPE);
        assertThat(fileBackedTasksManager.getSubTaskById(2).getEpicId()).isEqualTo(1);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getSubTaskById(2))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE);
    }

    @Test
    void shouldDeleteAllTasks() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 2);
        fileBackedTasksManager.deleteAllTasks();

        assertThat(fileBackedTasksManager.getTaskById(1)).isEqualTo(null);
        assertThat(fileBackedTasksManager.getEpicById(2)).isEqualTo(null);
        assertThat(fileBackedTasksManager.getSubTaskById(3)).isEqualTo(null);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getTaskById(1)).isEqualTo(null);
        assertThat(fileBackedTasksManager1.getEpicById(2)).isEqualTo(null);
        assertThat(fileBackedTasksManager1.getSubTaskById(3)).isEqualTo(null);
    }

    @Test
    void shouldDeleteTaskById() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        fileBackedTasksManager.deleteTaskById(1);

        assertThat(fileBackedTasksManager.getSubTaskById(1)).isEqualTo(null);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getSubTaskById(1)).isEqualTo(null);
    }

    @Test
    void shouldDeleteEpicById() {
        fileBackedTasksManager.createEpic("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        fileBackedTasksManager.deleteEpicById(1);

        assertThat(fileBackedTasksManager.getEpicById(1)).isEqualTo(null);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getEpicById(1)).isEqualTo(null);
    }

    @Test
    void shouldDeleteSubTaskById() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
        fileBackedTasksManager.deleteSubTaskById(2);

        assertThat(fileBackedTasksManager.getSubTaskById(2)).isEqualTo(null);
    }

    @Test
    void shouldUpdateTaskById() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.TASK_TYPE);
        Task testTask = fileBackedTasksManager.getTaskById(1);
        assertThat(testTask)
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.TASK_TYPE);
        fileBackedTasksManager.updateTaskById(1, "Сходил в кино", "Купил билеты", TaskStatus.DONE);
        assertThat(testTask)
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходил в кино", "Купил билеты", TaskStatus.DONE, TaskType.TASK_TYPE);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходил в кино", "Купил билеты", TaskStatus.DONE, TaskType.TASK_TYPE);
    }

    @Test
    void shouldUpdateEpicById() {
        fileBackedTasksManager.createEpic("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.EPIC_TYPE);
        Epic testEpic = fileBackedTasksManager.getEpicById(1);
        assertThat(testEpic)
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.EPIC_TYPE);
        fileBackedTasksManager.updateEpicById(1, "Сходил в кино", "Купил билеты", TaskStatus.DONE);
        assertThat(testEpic)
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходил в кино", "Купил билеты", TaskStatus.DONE, TaskType.EPIC_TYPE);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходил в кино", "Купил билеты", TaskStatus.DONE, TaskType.EPIC_TYPE);
    }

    @Test
    void shouldUpdateSubTaskById() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.NEW, TaskType.SUBTASK_TYPE, 1);
        SubTask testSubTask = fileBackedTasksManager.getSubTaskById(2);
        assertThat(testSubTask)
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", TaskStatus.NEW, TaskType.SUBTASK_TYPE, 1);
        fileBackedTasksManager.updateSubTaskById(2, "Сделал полы", "Полы из досок", TaskStatus.DONE);
        assertThat(testSubTask)
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделал полы", "Полы из досок", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getSubTaskById(2))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделал полы", "Полы из досок", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
    }

    @Test
    void shouldGetTaskById() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.TASK_TYPE);
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        Task testTask = fileBackedTasksManager1.getTaskById(1);

        assertThat(testTask)
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.TASK_TYPE);
    }

    @Test
    void shouldGetEpicById() {
        fileBackedTasksManager.createEpic("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.EPIC_TYPE);
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        Epic testEpic = fileBackedTasksManager1.getEpicById(1);

        assertThat(testEpic)
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходить в кино", "Купить билеты", TaskStatus.NEW, TaskType.EPIC_TYPE);
    }

    @Test
    void shouldGetSubTaskById() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        SubTask testSubTask = fileBackedTasksManager1.getSubTaskById(2);

        assertThat(testSubTask)
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
    }

    @Test
    void shouldGetSubTasksInEpic() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
        fileBackedTasksManager.createSubTask("Поклеить обои", "Обои красивые", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 1);
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.epic.get(1).getSubTaskIds())
                .containsExactlyInAnyOrder(2, 3);
    }
}