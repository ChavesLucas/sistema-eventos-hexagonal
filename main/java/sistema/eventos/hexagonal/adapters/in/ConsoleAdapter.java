
package sistema.eventos.hexagonal.adapters.in;

import sistema.eventos.hexagonal.application.EventService;
import sistema.eventos.hexagonal.application.UserService;
import sistema.eventos.hexagonal.domain.Event;
import sistema.eventos.hexagonal.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleAdapter {
    private final UserService userService;
    private final EventService eventService;
    private final Scanner scanner = new Scanner(System.in);
    private User currentUser;

    public ConsoleAdapter(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    public void run() {
        System.out.println("=== Sistema de Eventos (Hexagonal) ===");
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                running = authMenu();
            } else {
                running = mainMenu();
            }
        }
        System.out.println("Encerrando. Até mais!");
    }

    private boolean authMenu() {
        System.out.println("\n[1] Registrar  [2] Login  [0] Sair");
        String opt = scanner.nextLine();
        try {
            switch (opt) {
                case "1":
                    System.out.print("Nome: "); String name = scanner.nextLine();
                    System.out.print("Email: "); String email = scanner.nextLine();
                    System.out.print("Senha: "); String pass = scanner.nextLine();
                    userService.register(name, email, pass);
                    System.out.println("Usuário registrado com sucesso.");
                    return true;
                case "2":
                    System.out.print("Email: "); String lemail = scanner.nextLine();
                    System.out.print("Senha: "); String lpass = scanner.nextLine();
                    Optional<User> user = userService.login(lemail, lpass);
                    if (user.isPresent()) {
                        currentUser = user.get();
                        System.out.println("Login efetuado. Bem-vindo, " + currentUser.getName() + "!");
                    } else {
                        System.out.println("Credenciais inválidas.");
                    }
                    return true;
                case "0":
                    return false;
                default:
                    System.out.println("Opção inválida");
                    return true;
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return true;
        }
    }

    private boolean mainMenu() {
        System.out.println("\n[1] Criar evento  [2] Listar eventos  [3] Meus eventos  [4] Deletar evento  [9] Logout  [0] Sair");
        String opt = scanner.nextLine();
        try {
            switch (opt) {
                case "1":
                    createEventFlow(); return true;
                case "2":
                    listEvents(eventService.listAll()); return true;
                case "3":
                    listEvents(eventService.listByUser(currentUser.getId())); return true;
                case "4":
                    System.out.print("ID do evento: "); String id = scanner.nextLine();
                    eventService.delete(id); System.out.println("Evento removido (se existia)."); return true;
                case "9":
                    currentUser = null; System.out.println("Logout realizado."); return true;
                case "0":
                    return false;
                default:
                    System.out.println("Opção inválida"); return true;
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return true;
        }
    }

    private void createEventFlow() {
        System.out.print("Título: "); String title = scanner.nextLine();
        System.out.print("Descrição: "); String desc = scanner.nextLine();
        System.out.print("Categoria: "); String cat = scanner.nextLine();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.print("Início (yyyy-MM-dd HH:mm): "); String s = scanner.nextLine();
        System.out.print("Fim    (yyyy-MM-dd HH:mm): "); String e = scanner.nextLine();
        LocalDateTime start = LocalDateTime.parse(s, fmt);
        LocalDateTime end = LocalDateTime.parse(e, fmt);
        Event ev = eventService.create(title, desc, start, end, cat, currentUser.getId());
        System.out.println("Evento criado: " + ev.getId());
    }

    private void listEvents(List<Event> events) {
        if (events.isEmpty()) { System.out.println("Nenhum evento."); return; }
        for (Event ev : events) {
            System.out.printf("- [%s] %s | %s -> %s | %s | owner=%s%n",
                    ev.getId(),
                    ev.getTitle(),
                    ev.getStartAt(),
                    ev.getEndAt(),
                    ev.getStatus(),
                    ev.getOwnerUserId());
        }
    }
}
