
package sistema.eventos.hexagonal.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Event implements Serializable {
    private final String id;
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Category category;
    private String ownerUserId;

    public Event(String title, String description, LocalDateTime startAt, LocalDateTime endAt, Category category, String ownerUserId) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.category = category;
        this.ownerUserId = ownerUserId;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getStartAt() { return startAt; }
    public LocalDateTime getEndAt() { return endAt; }
    public Category getCategory() { return category; }
    public String getOwnerUserId() { return ownerUserId; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStartAt(LocalDateTime startAt) { this.startAt = startAt; }
    public void setEndAt(LocalDateTime endAt) { this.endAt = endAt; }
    public void setCategory(Category category) { this.category = category; }
    public void setOwnerUserId(String ownerUserId) { this.ownerUserId = ownerUserId; }

    public String getStatus() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startAt)) return "PRÓXIMO";
        if (now.isAfter(endAt)) return "FINALIZADO";
        return "EM ANDAMENTO";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + ''' +
                ", title='" + title + ''' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", status=" + getStatus() +
                '}';
    }
}
