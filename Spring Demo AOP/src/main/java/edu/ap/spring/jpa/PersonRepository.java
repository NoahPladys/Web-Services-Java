package edu.ap.spring.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.ap.spring.aop.Interceptable;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
	
	@Interceptable
	public Person findByName(String name);
}