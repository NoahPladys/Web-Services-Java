package com.example.grade.jpa;

import org.springframework.data.repository.CrudRepository;

public interface GradeRepository extends CrudRepository<Grade, Long> {
    public Grade findByFirstNameAndLastName(String firstName, String lastName);
}
