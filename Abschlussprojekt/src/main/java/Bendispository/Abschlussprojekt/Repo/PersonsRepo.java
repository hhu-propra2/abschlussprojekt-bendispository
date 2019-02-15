package Bendispository.Abschlussprojekt.Repo;

import Bendispository.Abschlussprojekt.Model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonsRepo extends CrudRepository<Person,Long> {
    List<Person> findAll();
}