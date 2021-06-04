package upce.semprace.eshop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import upce.semprace.eshop.dto.PridejZmenProduktDto;
import upce.semprace.eshop.entity.Produkt;
import upce.semprace.eshop.repository.ProduktRepository;
import upce.semprace.eshop.repository.VyrobceRepository;
import java.util.List;

@RestController
@RequestMapping("/produkt")
@CrossOrigin("http://localhost:3000")
public class ProduktController {
    @Autowired
    ProduktRepository produktRepository;

    @Autowired
    VyrobceRepository vyrobceRepository;

    @ExceptionHandler(RuntimeException.class)
    public String ochranaChyb() {
        return "chyba";
    }

    @PostMapping(value = {"", "/"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String zpracujProdukt(@RequestBody PridejZmenProduktDto pridejZmenProduktDto) {
        Produkt produkt = new Produkt();
        produkt.setId(pridejZmenProduktDto.getId());
        produkt.setNazev(pridejZmenProduktDto.getNazev());
        produkt.setPopis(pridejZmenProduktDto.getPopis());
        produkt.setCena(pridejZmenProduktDto.getCena());
        produkt.setSlevaProcenta(pridejZmenProduktDto.getSlevaProcenta());
        produkt.setvNabidce(pridejZmenProduktDto.isvNabidce());
        produkt.setCestaKObrazku(pridejZmenProduktDto.getCestaKObrazku());
        produkt.setVyrobce(vyrobceRepository.findById(pridejZmenProduktDto.getVyrobce()).get());
        produktRepository.save(produkt);
        return "redirect:/produkt";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String smazProdukt(@PathVariable(required = false) Long id, Model model) {
        produktRepository.deleteById(id);
        return "/";
    }

    @GetMapping(value = {"/all-products"})
    public List<Produkt> getProducts() {
        return produktRepository.findAll();
    }

    @GetMapping(value = {"", "/"})
    public List<Produkt> getTopProducts() {
        return produktRepository.findTop();
    }
}
