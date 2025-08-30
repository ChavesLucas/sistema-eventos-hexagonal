
package sistema.eventos.hexagonal.adapters.out;

import sistema.eventos.hexagonal.domain.Event;
import sistema.eventos.hexagonal.ports.EventRepositoryPort;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileEventRepository implements EventRepositoryPort {
    private final File storage;
    private final Map<String, Event> cache = new LinkedHashMap<>();

    public FileEventRepository(String filepath) {
        this.storage = new File(filepath);
        load();
    }

    @SuppressWarnings("unchecked")
    private void load() {
        if (!storage.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(storage))) {
            List<Event> list = (List<Event>) in.readObject();
            cache.clear();
            for (Event e : list) cache.put(e.getId(), e);
        } catch (Exception e) {
            System.err.println("Falha ao carregar eventos: " + e.getMessage());
        }
    }

    private void persist() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storage))) {
            out.writeObject(new ArrayList<>(cache.values()));
        } catch (Exception e) {
            System.err.println("Falha ao salvar eventos: " + e.getMessage());
        }
    }

    @Override
    public Event save(Event event) {
        cache.put(event.getId(), event);
        persist();
        return event;
    }

    @Override
    public Optional<Event> findById(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public List<Event> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void deleteById(String id) {
        cache.remove(id);
        persist();
    }

    @Override
    public List<Event> findByOwnerUserId(String userId) {
        List<Event> all = new ArrayList<>(cache.values());
        List<Event> result = new ArrayList<>();
        for (Event e : all) {
            if (e.getOwnerUserId() != null && e.getOwnerUserId().equals(userId)) {
                result.add(e);
            }
        }
        return result;
    }
}
