
package sistema.eventos.hexagonal.adapters.out;

import sistema.eventos.hexagonal.domain.User;
import sistema.eventos.hexagonal.ports.UserRepositoryPort;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileUserRepository implements UserRepositoryPort {
    private final File storage;
    private final Map<String, User> cache = new LinkedHashMap<>();

    public FileUserRepository(String filepath) {
        this.storage = new File(filepath);
        load();
    }

    @SuppressWarnings("unchecked")
    private void load() {
        if (!storage.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(storage))) {
            List<User> list = (List<User>) in.readObject();
            cache.clear();
            for (User u : list) cache.put(u.getId(), u);
        } catch (Exception e) {
            System.err.println("Falha ao carregar usuários: " + e.getMessage());
        }
    }

    private void persist() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storage))) {
            out.writeObject(new ArrayList<>(cache.values()));
        } catch (Exception e) {
            System.err.println("Falha ao salvar usuários: " + e.getMessage());
        }
    }

    @Override
    public User save(User user) {
        cache.put(user.getId(), user);
        persist();
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return cache.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void deleteById(String id) {
        cache.remove(id);
        persist();
    }
}
