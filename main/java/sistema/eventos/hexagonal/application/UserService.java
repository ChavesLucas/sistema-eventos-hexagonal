
package sistema.eventos.hexagonal.application;

import sistema.eventos.hexagonal.domain.User;
import sistema.eventos.hexagonal.ports.UserRepositoryPort;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepositoryPort userRepository;

    public UserService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String name, String email, String password) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email é obrigatório");
        if (password == null || password.length() < 4) throw new IllegalArgumentException("Senha deve ter ao menos 4 caracteres");
        if (userRepository.findByEmail(email).isPresent()) throw new IllegalArgumentException("Email já cadastrado");
        return userRepository.save(new User(name, email, password));
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email).filter(u -> u.getPassword().equals(password));
    }

    public List<User> listAll() { return userRepository.findAll(); }

    public void delete(String id) { userRepository.deleteById(id); }
}
