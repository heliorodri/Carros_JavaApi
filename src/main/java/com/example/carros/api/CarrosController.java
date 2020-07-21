package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService service;

    @GetMapping()
    public ResponseEntity<Iterable<Carro>> get() {
        return ResponseEntity.ok(service.getCarros());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        return service.getById(id).map(c -> ResponseEntity.ok(c)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getByTipo(@PathVariable("tipo") String tipo) {
        List<Carro> carros = service.getByTipo(tipo);

        return carros.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(carros);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Carro carro) {
        Carro c = service.save(carro);
        return (ResponseEntity) ResponseEntity.created(URI.create("Carro inserido com sucesso! Id: " + c.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> put(@PathVariable("id") Long id, @RequestBody Carro carro) {
        Carro c = service.update(carro, id);
        return new ResponseEntity<>("Carro atualizado com sucesso! Id: " + c.getId(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>("Carro removido com sucesso!", HttpStatus.OK);
    }

}
