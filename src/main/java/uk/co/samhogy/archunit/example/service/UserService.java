package uk.co.samhogy.archunit.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.samhogy.archunit.example.domain.User;
import uk.co.samhogy.archunit.example.dto.UserDTO;
import uk.co.samhogy.archunit.example.entity.UserEntity;
import uk.co.samhogy.archunit.example.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User getUserDetails(int id) {
        UserEntity user = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No user found with ID " + id)
        );
        return new User(user.getId(), user.getUsername());
    }
}
