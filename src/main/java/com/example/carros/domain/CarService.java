package com.example.carros.domain;

import com.example.carros.domain.dto.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository repository;

    public List<CarDTO> findAll() {
        return repository.findAll().stream()
                .map(CarDTO::create)
                .collect(Collectors.toList());
    }

    public Optional<CarDTO> findById(Long id) {
        return repository.findById(id)
                .map(CarDTO::create);
    }

    public List<CarDTO> findByType(String tipo) {
        return repository.findByTipo(tipo).stream()
                .map(CarDTO::create)
                .collect(Collectors.toList());
    }

    public Optional<CarDTO> insert(Car carro) {
        Car savedCar = repository.save(carro);
        return Optional.of(CarDTO.create(savedCar));
    }

     public Optional<CarDTO> update(Car car, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro!");

        return findById(id)
                .map(carDTO -> {
                    Car dbCar = Car.builder()
                            .name(car.getName())
                            .type(car.getType())
                            .description(car.getDescription())
                            .urlPicture(car.getUrlPicture())
                            .build();

                    repository.save(dbCar);
                    return CarDTO.create(dbCar);
                })
                .stream()
                .findFirst();
    }

    public void delete(Long id) {
        repository.findById(id)
                .ifPresent(car -> repository.deleteById(car.getId()));
    }
}
