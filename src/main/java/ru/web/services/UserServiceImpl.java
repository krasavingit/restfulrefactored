package ru.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.web.models.User;
import ru.web.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByFirstName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findOne(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void edit(User user) {
        User userExist = userRepository.findUserById(user.getId());
        userExist.setFirstName(user.getFirstName());
        userExist.setLastname(user.getLastname());
        userExist.setEmail(user.getEmail());
        if (user.getAge() == 0) {
            userExist.setAge(userRepository.findUserById(user.getId()).getAge());
        } else {
            userExist.setAge(user.getAge());
        }

        userExist.setPassword(user.getPassword());
        userExist.setRoles(user.getRoles());
        userRepository.save(userExist);
    }
}
