
package sistema.eventos.hexagonal.application;

import sistema.eventos.hexagonal.domain.User;
import sistema.eventos.hexagonal.ports.UserRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class InMemoryUserRepo implements UserRepositoryPort {
    private final Map<String, User> map = new HashMap<>();
    @Override public User save(User user) { map.put(user.getId(), user); return user; }
    @Override public Optional<User> findByEmail(String email) {
        return map.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }
    @Override public Optional<User> findById(String id) { return Optional.ofNullable(map.get(id)); }
    @Override public List<User> findAll() { return new ArrayList<>(map.values()); }
    @Override public void deleteById(String id) { map.remove(id); }
}

public class UserServiceTest {
    @Test
    void register_and_login_ok() {
        UserService service = new UserService(new InMemoryUserRepo());
        User u = service.register("Ana", "ana@mail.com", "1234");
        Assertions.assertNotNull(u.getId());
        Assertions.assertTrue(service.login("ana@mail.com","1234").isPresent());
    }

    @Test
    void duplicate_email_should_fail() {
        UserService service = new UserService(new InMemoryUserRepo());
        service.register("Ana", "ana@mail.com", "1234");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.register("Ana 2", "ana@mail.com", "1234"));
    }
}
