public class Managers {

    static InMemoryHistoryManager historyFromManagers = new InMemoryHistoryManager();
    public static HistoryManager getDefaultHistory() {
        return historyFromManagers;
    }

}
