package fi.haagahelia.ajokilometrit.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KilometritRepository extends CrudRepository<Kilometrit, Long> {
	List<Kilometrit> findByAuthor(String author);
}
