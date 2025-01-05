public class Managers {

    public static HistoryManager getDefaultHistory() {
        InMemoryHistoryManager history = new InMemoryHistoryManager();

        return history;
    }

}
