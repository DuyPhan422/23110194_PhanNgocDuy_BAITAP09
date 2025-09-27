package vn.iotstar.repository;

import vn.iotstar.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}