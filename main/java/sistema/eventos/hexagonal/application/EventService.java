
package sistema.eventos.hexagonal.application;

import sistema.eventos.hexagonal.domain.Category;
import sistema.eventos.hexagonal.domain.Event;
import sistema.eventos.hexagonal.ports.EventRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EventService {
    private final EventRepositoryPort eventRepository;

    public EventService(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event create(String title, String description, LocalDateTime startAt, LocalDateTime endAt, String categoryName, String ownerUserId) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Título é obrigatório");
        if (startAt == null || endAt == null || endAt.isBefore(startAt)) throw new IllegalArgumentException("Período inválido");
        Category category = new Category(categoryName == null || categoryName.isBlank() ? "Geral" : categoryName);
        Event e = new Event(title, description, startAt, endAt, category, ownerUserId);
        return eventRepository.save(e);
    }

    public List<Event> listAll() { return eventRepository.findAll(); }

    public Optional<Event> findById(String id) { return eventRepository.findById(id); }

    public void delete(String id) { eventRepository.deleteById(id); }

    public List<Event> listByUser(String userId) { return eventRepository.findByOwnerUserId(userId); }
}
