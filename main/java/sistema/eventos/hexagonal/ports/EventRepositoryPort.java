
package sistema.eventos.hexagonal.ports;

import sistema.eventos.hexagonal.domain.Event;
import java.util.List;
import java.util.Optional;

public interface EventRepositoryPort {
    Event save(Event event);
    Optional<Event> findById(String id);
    List<Event> findAll();
    void deleteById(String id);
    List<Event> findByOwnerUserId(String userId);
}
