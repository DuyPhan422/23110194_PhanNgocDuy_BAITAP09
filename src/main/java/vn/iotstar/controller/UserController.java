package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @QueryMapping
    public List<User> users() {
        return userService.getUsers();
    }

    @QueryMapping
    public User user(@Argument Long id) {
        return userService.getUser(id);
    }

    @MutationMapping
    public User createUser(@Argument String fullname, @Argument String email, @Argument String phone, @Argument String password) {
        return userService.createUser(fullname, email, phone, password);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument String fullname, @Argument String email, @Argument String phone) {
        return userService.updateUser(id, fullname, email, phone);
    }

    @MutationMapping
    public boolean deleteUser(@Argument Long id) {
        return userService.deleteUser(id);
    }
}