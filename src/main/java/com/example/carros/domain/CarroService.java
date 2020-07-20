package com.example.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public Iterable<Carro> getCarros() {
        return rep.findAll();
    }

    public List<Carro> getCarrosFake() {
        List<Carro> carros = new ArrayList<>();

        carros.add(new Carro(1L, "Focus"));
        carros.add(new Carro(2L, "Fusion"));
        carros.add(new Carro(3L, "Fiesta"));

        return carros;
    }
}
