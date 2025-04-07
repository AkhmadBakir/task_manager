import model.Task;
import org.junit.jupiter.api.Test;
import service.impl.InMemoryHistoryManager;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class InMemoryHistoryManagerTest {

    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    Task testTask = new Task("Сходить в кино", "Купить билеты");

    @Test
    void add() {
         inMemoryHistoryManager.add(testTask);
         assertThat(inMemoryHistoryManager.getTasks())
                 .hasSize(1)
                 .containsExactly(testTask);
    }

    @Test
    void remove() {
        testTask.setId(1);
        inMemoryHistoryManager.add(testTask);
        inMemoryHistoryManager.remove(1);
        assertThat(inMemoryHistoryManager.getTasks())
                .hasSize(0);
    }

    @Test
    void getTasks() {
        inMemoryHistoryManager.add(testTask);
        assertThat(inMemoryHistoryManager.getTasks())
                .hasSize(1)
                .containsExactly(testTask);
    }

    @Test
    void removeAll() {
        inMemoryHistoryManager.add(testTask);
        inMemoryHistoryManager.removeAll();
        assertThat(inMemoryHistoryManager.getTasks())
                .hasSize(0);
    }
}