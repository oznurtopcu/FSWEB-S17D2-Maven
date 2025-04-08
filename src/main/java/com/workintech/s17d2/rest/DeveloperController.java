package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.JuniorDeveloper;
import com.workintech.s17d2.model.MidDeveloper;
import com.workintech.s17d2.model.SeniorDeveloper;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DeveloperController {

    public Map<Integer, Developer> developers;
    private Taxable taxable;

    @PostConstruct
    public void init() {
        this.developers = new HashMap<>();
    }

    @Autowired
    public  DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    @GetMapping("/developers")
    public List<Developer> getDevelopers() {
        return new ArrayList<>(developers.values());
    }

    @GetMapping("/developers/{id}")
    public Developer getDeveloperById(@PathVariable("id") int id) {
        return developers.get(id);
    }

    @PostMapping("/developers")
    public ResponseEntity<Developer> addDeveloper(@RequestBody Developer developer) {
        Developer newDev=null;
        switch (developer.getExperience()) {
            case JUNIOR:
                newDev = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary()- taxable.getSimpleTaxRate());
                break;
            case MID:
                newDev = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary()- taxable.getMiddleTaxRate());
                break;
            case SENIOR:
                newDev = new SeniorDeveloper(developer.getId(), developer.getName(), developer.getSalary()- taxable.getUpperTaxRate());
                break;
        }
        developers.put(developer.getId(),newDev);

        return ResponseEntity.status(HttpStatus.CREATED).body(newDev);
    }

    @PutMapping("/developers/{id}")
    public Developer updateDeveloper(@PathVariable("id") int id, @RequestBody Developer newDeveloper) {
        developers.replace(id, newDeveloper);
        return developers.get(id);
    }

    @DeleteMapping("/developers/{id}")
    public void deleteDeveloper(@PathVariable("id") int id) {
        developers.remove(id);
    }

}
