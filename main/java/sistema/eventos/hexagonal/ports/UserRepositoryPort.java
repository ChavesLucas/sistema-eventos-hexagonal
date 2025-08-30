
package sistema.eventos.hexagonal.ports;

import sistema.eventos.hexagonal.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    List<User> findAll();
    void deleteById(String id);
}
