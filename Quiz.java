package model;

public class Quiz {
    private int id;
    private String title;
    private String description;
    private int timeLimitMinutes;

    public Quiz() {}

    public Quiz(int id, String title, String description, int timeLimitMinutes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timeLimitMinutes = timeLimitMinutes;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getTimeLimitMinutes() { return timeLimitMinutes; }
    public void setTimeLimitMinutes(int timeLimitMinutes) { this.timeLimitMinutes = timeLimitMinutes; }

    @Override
    public String toString() {
        return title;
    }
}
