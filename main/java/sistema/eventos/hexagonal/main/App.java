
package sistema.eventos.hexagonal.main;

import sistema.eventos.hexagonal.adapters.in.ConsoleAdapter;
import sistema.eventos.hexagonal.adapters.out.FileEventRepository;
import sistema.eventos.hexagonal.adapters.out.FileUserRepository;
import sistema.eventos.hexagonal.application.EventService;
import sistema.eventos.hexagonal.application.UserService;
import sistema.eventos.hexagonal.ports.EventRepositoryPort;
import sistema.eventos.hexagonal.ports.UserRepositoryPort;

public class App {
    public static void main(String[] args) {
        String dataDir = System.getProperty("user.home") + "/.eventos-data";
        java.io.File dir = new java.io.File(dataDir);
        if (!dir.exists()) dir.mkdirs();

        UserRepositoryPort userRepo = new FileUserRepository(dataDir + "/users.data");
        EventRepositoryPort eventRepo = new FileEventRepository(dataDir + "/events.data");

        UserService userService = new UserService(userRepo);
        EventService eventService = new EventService(eventRepo);

        ConsoleAdapter console = new ConsoleAdapter(userService, eventService);
        console.run();
    }
}
