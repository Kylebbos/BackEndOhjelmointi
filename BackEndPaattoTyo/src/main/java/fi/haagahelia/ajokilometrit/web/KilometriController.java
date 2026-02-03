package fi.haagahelia.ajokilometrit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fi.haagahelia.ajokilometrit.domain.Fuel;
import fi.haagahelia.ajokilometrit.domain.FuelRepository;
import fi.haagahelia.ajokilometrit.domain.Kilometrit;
import fi.haagahelia.ajokilometrit.domain.KilometritRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class KilometriController {

	private final KilometritRepository kilometritRepository;
	private final FuelRepository fuelRepository;

	@Autowired
	public KilometriController(KilometritRepository kilometritRepository, FuelRepository fuelRepository) {
		this.kilometritRepository = kilometritRepository;
		this.fuelRepository = fuelRepository;
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@GetMapping("/kilometritlist")
	public String kilometritList(Model model) {
		Iterable<Kilometrit> kilometrit = kilometritRepository.findAll();
		model.addAttribute("kilometrit", kilometrit);
		return "kilometritlist";
	}

	@GetMapping("/addkilometrit")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String showAddKilometritForm(Model model) {
		Iterable<Fuel> fueltypes = fuelRepository.findAll();
		model.addAttribute("fueltypes", fueltypes);
		return "addkilometrit";
	}

	@PostMapping("/addkilometrit")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addKilometrit(@ModelAttribute Kilometrit kilometrit) {
		kilometrit.calculateDerivedFields(kilometritRepository);
		kilometritRepository.save(kilometrit);
		return "redirect:/kilometritlist";
	}

	@PostMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteKilometrit(@PathVariable Long id) {
		kilometritRepository.deleteById(id);
		return "redirect:/kilometritlist";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editKilometrit(@PathVariable Long id, Model model) {
		Kilometrit kilometrit = kilometritRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid kilometri ID"));
		model.addAttribute("kilometrit", kilometrit);
		Iterable<Fuel> fueltypes = fuelRepository.findAll();
		model.addAttribute("fueltypes", fueltypes);
		return "editkilometrit";
	}

	@PostMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String updateKilometrit(@PathVariable Long id, @ModelAttribute Kilometrit updatedKilometrit) {

		Kilometrit existingKilometrit = kilometritRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid kilometri ID"));

		existingKilometrit.setDate(updatedKilometrit.getDate());
		existingKilometrit.setCar(updatedKilometrit.getCar());
		existingKilometrit.setOdometerReading(updatedKilometrit.getOdometerReading());
		existingKilometrit.setLitersFueled(updatedKilometrit.getLitersFueled());
		existingKilometrit.setPrice(updatedKilometrit.getPrice());
		existingKilometrit.setFuel(updatedKilometrit.getFuel());

		kilometritRepository.save(existingKilometrit);

		return "redirect:/kilometritlist";
	}

	@GetMapping("/api/kilometrit")
	public @ResponseBody List<Kilometrit> getAllKilometrit() {
		return (List<Kilometrit>) kilometritRepository.findAll();
	}

	@GetMapping("/api/kilometrit/{id}")
	public @ResponseBody ResponseEntity<Kilometrit> getKilometritById(@PathVariable Long id) {
		Optional<Kilometrit> kilometrit = kilometritRepository.findById(id);
		return kilometrit.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}