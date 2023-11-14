package server.storage;

public interface Repo {
    void save(String text);
    String load();
}
