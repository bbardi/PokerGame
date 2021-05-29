package edu.bbardi.pokerbackend.user;

import edu.bbardi.pokerbackend.user.model.dto.UserDTO;
import edu.bbardi.pokerbackend.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.bbardi.pokerbackend.UrlMapping.ENTITY;
import static edu.bbardi.pokerbackend.UrlMapping.USER;

@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class UserController {
    private final UserManagementService userManagementService;

    @GetMapping
    public List<UserDTO> findAllUsers() {
        return userManagementService.findAll();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userManagementService.createUser(user);
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id) {
        userManagementService.deleteUser(id);
    }

    @PutMapping(ENTITY)
    public UserDTO editUser(@PathVariable Long id, @RequestBody UserDTO user) {
        return userManagementService.editUser(id, user);
    }
}
