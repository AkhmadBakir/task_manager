import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class InMemoryHistoryManagerTest {

    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    Task task = new Task(1, "Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
    @Test
    void add() {
         inMemoryHistoryManager.add(task);
         assertThat(inMemoryHistoryManager.getTasks())
                 .hasSize(1)
                 .containsExactly(task);
    }

    @Test
    void remove() {
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.remove(1);
        assertThat(inMemoryHistoryManager.getTasks())
                .hasSize(0);
    }

    @Test
    void getTasks() {
        inMemoryHistoryManager.add(task);
        assertThat(inMemoryHistoryManager.getTasks())
                .hasSize(1)
                .containsExactly(task);
    }

    @Test
    void removeAll() {
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.removeAll();
        assertThat(inMemoryHistoryManager.getTasks())
                .hasSize(0);
    }
}