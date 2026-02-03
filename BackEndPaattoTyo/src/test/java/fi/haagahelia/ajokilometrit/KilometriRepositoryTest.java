package fi.haagahelia.ajokilometrit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fi.haagahelia.ajokilometrit.domain.Fuel;
import fi.haagahelia.ajokilometrit.domain.FuelRepository;
import fi.haagahelia.ajokilometrit.domain.Kilometrit;
import fi.haagahelia.ajokilometrit.domain.KilometritRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "ADMIN")
public class KilometriRepositoryTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private KilometritRepository kilometritRepository;

	@Autowired
	private FuelRepository fuelRepository;

	@Test
	public void testGetAllKilometrit() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/kilometrit")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void createNewKilometrit() {
		Fuel fuel = new Fuel("Gas");
		fuelRepository.save(fuel);
		Kilometrit kilometrit = new Kilometrit("10-12-2020", "Car", 20230, 45, 2, kilometritRepository);
		kilometrit.setFuel(fuel);
		kilometritRepository.save(kilometrit);
		assertThat(kilometrit.getId()).isNotNull();
	}

	@Test
	public void deleteKilometrit() {
		Fuel fuel = new Fuel("Gas");
		fuelRepository.save(fuel);

		Kilometrit kilometrit = new Kilometrit("10-12-2020", "Car", 20230, 45, 2, kilometritRepository);
		kilometrit.setFuel(fuel);
		kilometritRepository.save(kilometrit);

		kilometritRepository.delete(kilometrit);
		List<Kilometrit> newKilometrit = kilometritRepository.findByCarOrderByDateDesc("Car");

		System.out.println("New Kilometrit List: " + newKilometrit);

		assertThat(newKilometrit).isEmpty();
	}

}
