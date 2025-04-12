import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import java.util.List;

class CustomLinkedListTest {

    util.CustomLinkedList<model.Task> linkedListTest;
    model.Task task1;
    model.Task task2;
    model.Task task3;

    @BeforeEach
    public void set() {
        linkedListTest = new util.CustomLinkedList<>(3);
        task1 = new model.Task("Сходить в кино", "Купить билеты");
        task2 = new model.Epic("Построить дом", "Дом должен быть большим");
        task3 = new model.SubTask("Сделать фундамент", "Фундамент из бетона", 6);
    }


    @Test
    void shouldLinkLast() {
        linkedListTest.linkLast(task1);
        linkedListTest.linkLast(task2);
        linkedListTest.linkLast(task3);
        List<model.Task> tasks = linkedListTest.getTasks();

        assertThat(tasks).hasSize(3)
                .containsExactly(task1, task2, task3);
    }

    @Test
    void shouldRemoveNode() {
        util.CustomLinkedList.Node<model.Task> node1 = linkedListTest.linkLast(task1);
        util.CustomLinkedList.Node<model.Task> node2 = linkedListTest.linkLast(task2);
        util.CustomLinkedList.Node<model.Task> node3 = linkedListTest.linkLast(task3);
        linkedListTest.removeNode(node1);
        List<model.Task> tasks = linkedListTest.getTasks();

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