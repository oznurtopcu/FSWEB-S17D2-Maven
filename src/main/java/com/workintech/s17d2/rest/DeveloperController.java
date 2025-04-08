package com.workintech.s17d2.rest;

import com.workintech.s17d2.dto.DeveloperResponse;
import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/developers")   //localhost:8585/workintech/developers/.......
public class DeveloperController {

    //normalde bu mapler private, teste lazım olduğu için public yaptık
    public Map<Integer, Developer> developers;
    private Taxable taxable;


    @Autowired
    public  DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    //uygulama ayağa kalkarken yapılması istenenler burada yapılıyor
    @PostConstruct
    public void init() {
        this.developers = new HashMap<>();
        //this.developers.put(1, new JuniorDeveloper(1, "öznur", 1000d));
    }

    @GetMapping
    public List<Developer> getDevelopers() {
        //developers.values().stream().toList();
        return new ArrayList<>(developers.values());
    }

    @GetMapping("/{id}")
    public Developer getDeveloperById(@PathVariable("id") int id) {
        return developers.get(id);
    }

//    @GetMapping("/{id}")
//    public DeveloperResponse getById(@PathVariable("id") int id) {
//        Developer found =  developers.get(id);
//        if(found==null) {
//            return new DeveloperResponse(null, HttpStatus.NOT_FOUND.value(), id + " ile kayıt bulunamadı.");
//        }
//        return new DeveloperResponse(found,HttpStatus.OK.value(), id + " ile kayıt bulundu");
//    }

    @PostMapping
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

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public DeveloperResponse save(@RequestBody Developer developer) {
//        Developer createdDeveloper = DeveloperFactory.createDeveloper(developer,taxable);
//        if(Objects.nonNull(createdDeveloper)) {
//            developers.put(createdDeveloper.getId(), createdDeveloper);
//        }
//        return new DeveloperResponse(createdDeveloper, HttpStatus.CREATED.value(),"create işlemi başarılı");
//    }

    @PutMapping("/{id}")
    public Developer updateDeveloper(@PathVariable("id") int id, @RequestBody Developer newDeveloper) {
        developers.replace(id, newDeveloper);
        return developers.get(id);
    }

//    @PutMapping("/{id}")
//    public DeveloperResponse update(@PathVariable("id") int id, @RequestBody Developer newDeveloper) {
//        newDeveloper.setId(id);
//        Developer newDev = DeveloperFactory.createDeveloper(newDeveloper, taxable);
//        this.developers.put(id, newDev);
//        return new DeveloperResponse(newDev, HttpStatus.OK.value(), "update başarılı");
//    }

    @DeleteMapping("/{id}")
    public void deleteDeveloper(@PathVariable("id") int id) {
        developers.remove(id);
    }

//    @DeleteMapping("/{id}")
//    public DeveloperResponse delete(@PathVariable("id") int id) {
//        Developer removedDev = this.developers.get(id);
//        this.developers.remove(id);
//        return new DeveloperResponse(removedDev, HttpStatus.NO_CONTENT.value(), "silme başarılı" );
//    }

}
