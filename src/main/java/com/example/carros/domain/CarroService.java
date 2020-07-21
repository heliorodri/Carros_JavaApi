package com.example.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    public List<Carro> getByTipo(String tipo) {
        return rep.findByTipo(tipo);
    }

    public Carro save(Carro carro) {
        return rep.save(carro);
    }

     public Carro update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível inserir o registro!");

        Optional<Carro> oCar = getById(id);
        if(oCar.isPresent()){
            Carro dbCar = oCar.get();
            dbCar.setNome(carro.getNome());
            dbCar.setTipo(carro.getTipo());

            rep.save(dbCar);
            return dbCar;
        }
        else throw new RuntimeException("Não foi possível atualizar o registro!");
    }

    public void delete(Long id) {
        Optional<Carro> oCar = rep.findById(id);
        if(oCar.isPresent()){
            rep.deleteById(id);
        }
    }
}
