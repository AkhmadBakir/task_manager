import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import java.util.List;

class CustomLinkedListTest {

    CustomLinkedList<Task> linkedListTest;
    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    public void set() {
        linkedListTest = new CustomLinkedList<>(3);
        task1 = new Task(1, "Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
        task2 = new Epic(2, "Построить дом", "Дом должен быть большим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
        task3 = new SubTask(3, "Сделать фундамент", "Фундамент из бетона", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 6);
    }


    @Test
    void shouldLinkLast() {
        linkedListTest.linkLast(task1);
        linkedListTest.linkLast(task2);
        linkedListTest.linkLast(task3);
        List<Task> tasks = linkedListTest.getTasks();

        assertThat(tasks).hasSize(3)
                .containsExactly(task1, task2, task3);
    }

    @Test
    void shouldRemoveNode() {
        CustomLinkedList.Node<Task> node1 = linkedListTest.linkLast(task1);
        CustomLinkedList.Node<Task> node2 = linkedListTest.linkLast(task2);
        CustomLinkedList.Node<Task> node3 = linkedListTest.linkLast(task3);
        linkedListTest.removeNode(node1);
        List<Task> tasks = linkedListTest.getTasks();

        assertThat(tasks).hasSize(2)
                .containsExactly(task2, task3);
    }

    @Test
    void shouldGetTasks() {
        linkedListTest.linkLast(task1);
        linkedListTest.linkLast(task2);
        linkedListTest.linkLast(task3);

        assertThat(linkedListTest.getTasks()).hasSize(3)
                .containsExactly(task1, task2, task3);
    }

    @Test
    void shouldClear() {
        linkedListTest.linkLast(task1);
        linkedListTest.linkLast(task2);
        linkedListTest.linkLast(task3);
        linkedListTest.clear();

        assertThat(linkedListTest.getTasks()).isEmpty();
        assertThat(linkedListTest.size()).isEqualTo(0);
    }

    @Test
    void shouldSize() {
        linkedListTest.linkLast(task1);
        linkedListTest.linkLast(task2);
        linkedListTest.linkLast(task3);

        assertThat(linkedListTest.size()).isEqualTo(3);
    }

    @Test
    void shouldGetLimit() {
        linkedListTest.linkLast(task1);
        linkedListTest.linkLast(task2);
        linkedListTest.linkLast(task3);

        assertThat(linkedListTest.getLimit()).isEqualTo(3);
    }
}