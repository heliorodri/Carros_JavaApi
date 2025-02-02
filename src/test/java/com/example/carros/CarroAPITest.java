package com.example.carros;

import com.example.carros.domain.Car;
import com.example.carros.domain.CarService;
import com.example.carros.domain.dto.CarDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarroAPITest {

    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private CarService service;

    private ResponseEntity<CarDTO> getCarro(String url){
        return rest.getForEntity(url, CarDTO.class);
    }
    private ResponseEntity<List<CarDTO>> getCarros(String url){
        return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CarDTO>>() {});
    }

    //testar se os registros foram inseridos
    @Test
    public void testLista(){
        List<CarDTO> carros = getCarros("/api/v1/carros").getBody();

        assertNotNull(carros);
        assertEquals(30, carros.size());
    }


    //testar se existem 10 registros de cada tipo e o retorno de registro não existentes
    @Test
    public void testListaTipo(){
        assertEquals(10, getCarros("/api/v1/carros/tipo/esportivos").getBody().size());
        assertEquals(10, getCarros("/api/v1/carros/tipo/classicos").getBody().size());
        assertEquals(10, getCarros("/api/v1/carros/tipo/luxo").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getCarros("/api/v1/carros/tipo/xxx").getStatusCode());
    }


    //testar a busca de um unico registro (id = 11)
    @Test
    public void testGet() {
        ResponseEntity<CarDTO> response = getCarro("/api/v1/carros/11");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        CarDTO carro = response.getBody();
        assertEquals("Ferrari FF", carro.getName());
    }

    //testar NOT_FOUND
    @Test
    public void testNotFound() {
        ResponseEntity response = getCarro("/api/v1/carro/1000");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    //testar metodo save
    @Test
    public void save() {
        Car c = new Car();
        c.setNome("Kombi");
        c.setTipo("classicos");

        //insert
        ResponseEntity response = rest.postForEntity("/api/v1/carros", c, null);
        System.out.println(response);

        //testar se inseriu o registrou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        //buscar o registro e comparar valores
        String location = response.getHeaders().get("location").get(0);
        CarDTO car = getCarro(location).getBody();

        assertEquals(c.getNome(), car.getName());
        assertEquals(c.getTipo(), car.getType());

        //deletar registro
        rest.delete(location);

        //testar se deletou
        assertEquals(HttpStatus.NO_CONTENT, getCarro(location).getStatusCode());



    }
}
