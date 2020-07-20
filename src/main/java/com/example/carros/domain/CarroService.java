package com.example.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public Iterable<Carro> getCarros() {
        return rep.findAll();
    }

    public Optional<Carro> getById(Long id) {
        return rep.findById(id);
    }

    public Iterable<Carro> getByTipo(String tipo) {
        return rep.findByTipo(tipo);
    }

    public String save(Carro carro) {
        Carro c = rep.save(carro);
        return "Carro salvo com sucesso! Id: " + c.getId();
    }
}
