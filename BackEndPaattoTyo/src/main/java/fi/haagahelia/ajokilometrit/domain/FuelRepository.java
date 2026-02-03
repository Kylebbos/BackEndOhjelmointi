package fi.haagahelia.ajokilometrit.domain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepository extends CrudRepository<Fuel, Long> {
}

