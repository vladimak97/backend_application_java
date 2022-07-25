package com.logreghomeinpoland;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// RestController służy do tworzenia restful web services za pomocą adnotacji @RestController //
@RestController
public class UsersController {
    private PostgreSqlDao service = new PostgreSqlDao();

    // Metody z adnotacją PostMapping obsługują żądania HTTP POST dopasowane do podanego wyrażenia URI //

    @PostMapping("/register")
    public String register(User user) {
        service.save(user);
        return "OK";
    }

    @PostMapping("/login")
    public String login(User user){
        service.get(user.getEmail(), user.getPassword());
        return "OK";
    }

}
