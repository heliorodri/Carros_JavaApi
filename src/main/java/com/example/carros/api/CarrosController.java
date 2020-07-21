package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService service;
    
    @GetMapping()
    public Iterable<Carro> get() {
        return service.getCarros();
    }

    @GetMapping("/{id}")
    public Optional<Carro> getById(@PathVariable("id") Long id){
        return service.getById(id);
    }

    @GetMapping("/tipo/{tipo}")
    public Iterable<Carro> getByTipo(@PathVariable("tipo") String tipo) {
        return service.getByTipo(tipo);
    }

    @PostMapping
    public String post(@RequestBody Carro carro) {
        return service.save(carro);
    }

    @PutMapping("/{id}")
    public String put(@PathVariable("id") Long id, @RequestBody Carro carro){
        Carro c = service.update(carro, id);
        return "Carro atualizado com sucesso! Id: " + c.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        service.delete(id);
        return "Carro removido com sucesso!";
    }

}
