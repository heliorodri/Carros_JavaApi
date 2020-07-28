package com.example.carros.domain;

import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros() {
        return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public Optional<CarroDTO> getById(Long id) {
        return rep.findById(id).map(CarroDTO::create);
    }

    public List<CarroDTO> getByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO save(Carro carro) {
        CarroDTO cReturn = CarroDTO.create(rep.save(carro));
        Assert.notNull(cReturn.getId(), "Não foi possível inserir o registro!");
        return cReturn;
    }

     public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro!");

        Optional<CarroDTO> oCar = getById(id);
        if(oCar.isPresent()){
            Carro dbCar = new Carro();
            dbCar.setNome(carro.getNome());
            dbCar.setTipo(carro.getTipo());

            rep.save(dbCar);
            return CarroDTO.create(dbCar);
        }
        else return null;
    }

    public boolean delete(Long id) {
        Optional<Carro> oCar = rep.findById(id);
        if(oCar.isPresent()){
            rep.deleteById(id);
            return true;
        } else
            return false;
    }
}
