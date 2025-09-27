package vn.iotstar.service;

import vn.iotstar.entity.*;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User u);
    void delete(Long id);
}