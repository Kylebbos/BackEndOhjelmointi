package fi.haagahelia.ajokilometrit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.ajokilometrit.domain.AppUserRepository;
import fi.haagahelia.ajokilometrit.domain.Fuel;
import fi.haagahelia.ajokilometrit.domain.FuelRepository;
import fi.haagahelia.ajokilometrit.domain.Kilometrit;
import fi.haagahelia.ajokilometrit.domain.KilometritRepository;
import fi.haagahelia.ajokilometrit.domain.AppUser;

@SpringBootApplication
public class AjokilometritApplication {

	public static void main(String[] args) {
		SpringApplication.run(AjokilometritApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner kilometritDemo(AppUserRepository urepository, FuelRepository frepository, KilometritRepository kmRepository) {
		return (args) -> {
			
			frepository.save(new Fuel("e95"));
			frepository.save(new Fuel("e98"));
			frepository.save(new Fuel("Diesel"));
			frepository.save(new Fuel("e85"));
	
			
			AppUser user1 = new AppUser("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			AppUser user2 = new AppUser("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			urepository.save(user1);
			urepository.save(user2);
			
	        Iterable<Kilometrit> allKilometrit = kmRepository.findAll();
	        for (Kilometrit kilometrit : allKilometrit) {
	            kilometrit.calculateDerivedFields(kmRepository);
	            kmRepository.save(kilometrit);
	        }

		};
	}

}
