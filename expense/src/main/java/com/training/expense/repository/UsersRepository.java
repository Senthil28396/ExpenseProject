package com.training.expense.repository;

import java.text.Normalizer.Form;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.expense.model.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {

	List<Users> findByEmail(String email);

	Optional<Users> findById(int userId);

	Users getByEmail(String email);

	Users findByToken(String token);

}
