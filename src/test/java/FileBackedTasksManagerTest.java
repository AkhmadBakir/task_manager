import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.HistoryManager;
import service.impl.FileBackedTasksManager;
import util.Managers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import static enums.TaskStatus.*;
import static enums.TaskType.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class FileBackedTasksManagerTest {

    File file = new File("historyTestFileSave.csv");
    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

    @BeforeEach
    void init() {
        fileBackedTasksManager.getTask().clear();
        fileBackedTasksManager.getEpic().clear();
        fileBackedTasksManager.getSubTask().clear();

        try(FileWriter fileWriter = new FileWriter(file, false)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldFileBackedTasksManagerSaveInFile() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты");
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", 2);
        fileBackedTasksManager.save();
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        assertThat(fileBackedTasksManager1.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", NEW, TASK_TYPE);
        assertThat(fileBackedTasksManager1.getEpicById(2))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сделать ремонт", "Ремонт должен быть хорошим", NEW, EPIC_TYPE);
        assertThat(fileBackedTasksManager1.getSubTaskById(3))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", NEW, SUBTASK_TYPE, 2);
    }

    @Test
    void shouldMakeTaskFromString() {
        String string = "1,Сходить в кино,Купить билеты,IN_PROGRESS,TASK_TYPE";
        Task testTask = FileBackedTasksManager.fromString(string);
        assertThat(testTask)
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", IN_PROGRESS, TASK_TYPE);
    }

    @Test
    void shouldRecordHistoryToString() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        historyManager.add(fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты"));
        historyManager.add(fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим"));
        historyManager.add(fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", 2));
        String historyForString = FileBackedTasksManager.historyToString(historyManager);
        assertThat(historyForString).isEqualTo("1,2,3");
    }

    @Test
    void shouldLoadHistoryFromString() {
        String value = "1,2,3";
        List<Task> history = FileBackedTasksManager.historyFromString(value);
        assertThat(history.size()).isEqualTo(3);
    }

    @Test
    void shouldLoadFileBackedTasksManagerFromFile() {
        File file1 = new File("historyTestFile.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file1);
        assertThat(fileBackedTasksManager.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты",NEW, TASK_TYPE);
        assertThat(fileBackedTasksManager.getEpicById(2))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сделать ремонт", "Ремонт должен быть хорошим",NEW, EPIC_TYPE);
        assertThat(fileBackedTasksManager.getSubTaskById(3))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината",NEW, SUBTASK_TYPE, 2);
    }

    @Test
    void shouldCreateTask() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты");

        assertThat(fileBackedTasksManager.getTaskById(1).getId()).isEqualTo(1);
        assertThat(fileBackedTasksManager.getTaskById(1).getName()).isEqualTo("Сходить в кино");
        assertThat(fileBackedTasksManager.getTaskById(1).getDescription()).isEqualTo("Купить билеты");
        assertThat(fileBackedTasksManager.getTaskById(1).getStatus()).isEqualTo(NEW);
        assertThat(fileBackedTasksManager.getTaskById(1).getType()).isEqualTo(TASK_TYPE);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", NEW, TASK_TYPE);
    }

    @Test
    void shouldCreateEpic() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");

        assertThat(fileBackedTasksManager.getEpicById(1).getId()).isEqualTo(1);
        assertThat(fileBackedTasksManager.getEpicById(1).getName()).isEqualTo("Сделать ремонт");
        assertThat(fileBackedTasksManager.getEpicById(1).getDescription()).isEqualTo("Ремонт должен быть хорошим");
        assertThat(fileBackedTasksManager.getEpicById(1).getStatus()).isEqualTo(NEW);
        assertThat(fileBackedTasksManager.getEpicById(1).getType()).isEqualTo(EPIC_TYPE);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сделать ремонт", "Ремонт должен быть хорошим", NEW, EPIC_TYPE);
    }

    @Test
    void shouldCreateSubTask() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", 1);

        assertThat(fileBackedTasksManager.getSubTaskById(2).getId()).isEqualTo(2);
        assertThat(fileBackedTasksManager.getSubTaskById(2).getName()).isEqualTo("Сделать полы");
        assertThat(fileBackedTasksManager.getSubTaskById(2).getDescription()).isEqualTo("Полы из ламината");
        assertThat(fileBackedTasksManager.getSubTaskById(2).getStatus()).isEqualTo(NEW);
        assertThat(fileBackedTasksManager.getSubTaskById(2).getType()).isEqualTo(SUBTASK_TYPE);
        assertThat(fileBackedTasksManager.getSubTaskById(2).getEpicId()).isEqualTo(1);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getSubTaskById(2))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сделать полы", "Полы из ламината", NEW, SUBTASK_TYPE);
    }

    @Test
    void shouldDeleteAllTasks() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты");
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", 2);
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
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты");
        fileBackedTasksManager.deleteTaskById(1);

        assertThat(fileBackedTasksManager.getSubTaskById(1)).isEqualTo(null);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getSubTaskById(1)).isEqualTo(null);
    }

    @Test
    void shouldDeleteEpicById() {
        fileBackedTasksManager.createEpic("Сходить в кино", "Купить билеты");
        fileBackedTasksManager.deleteEpicById(1);

        assertThat(fileBackedTasksManager.getEpicById(1)).isEqualTo(null);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getEpicById(1)).isEqualTo(null);
    }

    @Test
    void shouldDeleteSubTaskById() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", 1);
        fileBackedTasksManager.deleteSubTaskById(2);

        assertThat(fileBackedTasksManager.getSubTaskById(2)).isEqualTo(null);
    }

    @Test
    void shouldUpdateTaskById() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты");
        assertThat(fileBackedTasksManager.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", NEW, TASK_TYPE);
        Task task = new Task("Сходил в кино", "Купил билеты");
        task.setId(1);
        task.setStatus(DONE);
        fileBackedTasksManager.updateTaskById(task);
        assertThat(fileBackedTasksManager.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходил в кино", "Купил билеты", DONE, TASK_TYPE);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getTaskById(1))
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходил в кино", "Купил билеты", DONE, TASK_TYPE);
    }

    @Test
    void shouldUpdateEpicById() {
        fileBackedTasksManager.createEpic("Сходить в кино", "Купить билеты");
        assertThat(fileBackedTasksManager.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходить в кино", "Купить билеты", NEW, EPIC_TYPE);
        Epic epic = new Epic("Сходил в кино", "Купил билеты");
        epic.setId(1);
        epic.setStatus(DONE);
        fileBackedTasksManager.updateEpicById(epic);
        assertThat(fileBackedTasksManager.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходил в кино", "Купил билеты", DONE, EPIC_TYPE);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходил в кино", "Купил билеты", DONE, EPIC_TYPE);
    }

    @Test
    void shouldUpdateSubTaskById() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", 1);
        assertThat(fileBackedTasksManager.getSubTaskById(2))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", NEW, SUBTASK_TYPE, 1);
        SubTask subTask = new SubTask("Сделал полы", "Полы из досок", 1);
        subTask.setId(2);
        subTask.setStatus(DONE);
        fileBackedTasksManager.updateSubTaskById(subTask);
        assertThat(fileBackedTasksManager.getSubTaskById(2))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделал полы", "Полы из досок", DONE, SUBTASK_TYPE, 1);

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getSubTaskById(2))
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделал полы", "Полы из досок", DONE, SUBTASK_TYPE, 1);
    }

    @Test
    void shouldGetTaskById() {
        fileBackedTasksManager.createTask("Сходить в кино", "Купить билеты");
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        Task testTask = fileBackedTasksManager1.getTaskById(1);

        assertThat(testTask)
                .isNotNull()
                .extracting(Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly("Сходить в кино", "Купить билеты", NEW, TASK_TYPE);
    }

    @Test
    void shouldGetEpicById() {
        fileBackedTasksManager.createEpic("Сходить в кино", "Купить билеты");
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        Epic testEpic = fileBackedTasksManager1.getEpicById(1);

        assertThat(testEpic)
                .isNotNull()
                .extracting(Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly("Сходить в кино", "Купить билеты", NEW, EPIC_TYPE);
    }

    @Test
    void shouldGetSubTaskById() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", 1);
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        SubTask testSubTask = fileBackedTasksManager1.getSubTaskById(2);

        assertThat(testSubTask)
                .isNotNull()
                .extracting(SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly("Сделать полы", "Полы из ламината", NEW, SUBTASK_TYPE, 1);
    }

    @Test
    void shouldGetSubTasksInEpic() {
        fileBackedTasksManager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        fileBackedTasksManager.createSubTask("Сделать полы", "Полы из ламината", 1);
        fileBackedTasksManager.createSubTask("Поклеить обои", "Обои красивые", 1);
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);

        assertThat(fileBackedTasksManager1.getEpic().get(1).getSubTaskIds())
                .containsExactlyInAnyOrder(2, 3);
    }
}