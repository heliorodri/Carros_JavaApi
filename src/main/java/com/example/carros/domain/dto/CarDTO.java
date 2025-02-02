package com.example.carros.domain.dto;

import com.example.carros.domain.Car;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarDTO {
    private Long id;
    private String name;
    private String type;

    public static CarDTO create(Car c) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(c, CarDTO.class);
    }
}
