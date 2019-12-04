package uk.co.samhogy.archunit.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uk.co.samhogy.archunit.example.domain.User;
import uk.co.samhogy.archunit.example.dto.UserDTO;
import uk.co.samhogy.archunit.example.service.UserService;

@RestController
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user/{id}")
    public UserDTO getUserName(@PathVariable(value="id") int id) {
        User user = userService.getUserDetails(id);
        return new UserDTO(user.getId(), user.getUsername());
    }
}
