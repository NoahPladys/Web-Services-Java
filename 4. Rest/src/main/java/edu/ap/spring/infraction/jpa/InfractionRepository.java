package edu.ap.spring.infraction.jpa;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "infractions", path = "infractions")
public interface InfractionRepository extends PagingAndSortingRepository<Infraction, Long> {
    List<Infraction> findByYear(@Param("year") String year);
}