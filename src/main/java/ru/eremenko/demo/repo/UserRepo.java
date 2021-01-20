package ru.eremenko.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eremenko.demo.model.User;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
