package fi.haagahelia.ajokilometrit.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "fuel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kilometrit> kilometrit;

    public Fuel() {
    }

    public Fuel(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Kilometrit> getKilometrit() {
        return kilometrit;
    }

    public void setKilometrit(List<Kilometrit> kilometrit) {
        this.kilometrit = kilometrit;
    }
}

