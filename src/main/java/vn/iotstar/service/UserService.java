package vn.iotstar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(String fullname, String email, String phone, String password) {
        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User updateUser(Long id, String fullname, String email, String phone) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (fullname != null) {
                user.setFullname(fullname);
            }
            if (email != null) {
                user.setEmail(email);
            }
            if (phone != null) {
                user.setPhone(phone);
            }
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}