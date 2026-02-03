package fi.haagahelia.ajokilometrit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.ajokilometrit.domain.Kilometrit;
import fi.haagahelia.ajokilometrit.domain.KilometritRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class KilometriController {

	private final KilometritRepository kilometritRepository;

	public KilometriController(KilometritRepository kilometritRepository) {
		this.kilometritRepository = kilometritRepository;
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/index")
	public String index(Model model) {

		List<Kilometrit> kilometrit = new ArrayList<>();
		kilometrit.add(new Kilometrit("10.12.2000", 200505, "e98", 2.093));
		

		model.addAttribute("kilometrit", kilometrit);

		return "index";
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
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "addkilometrit";
    }
	
	@PostMapping("/addkilometrit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addKilometrit(@ModelAttribute Kilometrit kilometrit) {
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
        return "editkilometrit";
    }

	@PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateKilometrit(@PathVariable Long id, @ModelAttribute Kilometrit updatedKilometrit) {
        Kilometrit kilometrit = kilometritRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid kilometri ID"));
        kilometrit.setTitle(updatedKilometrit.getTitle());
        kilometrit.setAuthor(updatedKilometrit.getAuthor());
        kilometritRepository.save(kilometrit);
        return "redirect:/kilometritlist";
    }

	@GetMapping("/api/kilometrit")
	public @ResponseBody List<Kilometrit> getAllKilometrit() {
		return (List<Kilometrit>) kilometritRepository.findAll();
	}

	@GetMapping("/api/books/{id}")
	public @ResponseBody ResponseEntity<Kilometrit> getKilometritById(@PathVariable Long id) {
		Optional<Kilometrit> kilometrit = kilometritRepository.findById(id);
		if (kilometrit.isPresent()) {
			return new ResponseEntity<>(kilometrit.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
