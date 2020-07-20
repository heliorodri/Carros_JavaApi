package com.example.carros.domain;

import java.util.ArrayList;
import java.util.List;

public class CarroService {
    public List<Carro> getCarros() {
        List<Carro> carros = new ArrayList<>();

        carros.add(new Carro(1L, "Focus"));
        carros.add(new Carro(2L, "Fusion"));
        carros.add(new Carro(3L, "Fiesta"));

        return carros;
    }
}
