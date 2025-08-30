
package sistema.eventos.hexagonal.application;

import sistema.eventos.hexagonal.domain.Event;
import sistema.eventos.hexagonal.ports.EventRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

class InMemoryEventRepo implements EventRepositoryPort {
    private final Map<String, Event> map = new HashMap<>();
    @Override public Event save(Event e){ map.put(e.getId(), e); return e; }
    @Override public Optional<Event> findById(String id){ return Optional.ofNullable(map.get(id)); }
    @Override public List<Event> findAll(){ return new ArrayList<>(map.values()); }
    @Override public void deleteById(String id){ map.remove(id); }
    @Override public List<Event> findByOwnerUserId(String userId){
        List<Event> r = new ArrayList<>();
        for (Event e: map.values()) if (userId.equals(e.getOwnerUserId())) r.add(e);
        return r;
    }
}

public class EventServiceTest {
    @Test
    void create_event_ok() {
        EventService service = new EventService(new InMemoryEventRepo());
        Event e = service.create("Evento", "Desc",
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2),
                "Tech", "USER1");
        Assertions.assertNotNull(e.getId());
        Assertions.assertEquals(1, service.listAll().size());
    }

    @Test
    void invalid_period_should_fail() {
        EventService service = new EventService(new InMemoryEventRepo());
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            service.create("Evento","Desc",
                    LocalDateTime.now().plusHours(2),
                    LocalDateTime.now().plusHours(1),
                    "Tech","USER1")
        );
    }
}
