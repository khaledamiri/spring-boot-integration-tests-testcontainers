package tn.amiri.springbootintegrationteststestcontainers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.amiri.springbootintegrationteststestcontainers.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}