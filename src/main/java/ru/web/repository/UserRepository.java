package ru.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.web.models.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByFirstName(String username);
    User findUserById(Long id);
}
