package uk.co.samhogy.archunit.example.repository;

import org.springframework.data.repository.CrudRepository;
import uk.co.samhogy.archunit.example.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
}
