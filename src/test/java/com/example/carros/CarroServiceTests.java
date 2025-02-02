package com.example.carros;

import com.example.carros.domain.Car;
import com.example.carros.domain.CarService;
import com.example.carros.domain.dto.CarDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CarroServiceTests {

	@Autowired
	private CarService service;

	@Test
	public void testInsert() {

		Car c = new Car();
		c.setNome("Carro01");
		c.setTipo("classicos");

		CarDTO cInserted = service.insert(c);
		assertNotNull(cInserted);

		Long id = cInserted.getId();
		assertNotNull(id);

		//buscar obj
		Optional<CarDTO> oCar = service.findById(id);
		assertTrue(oCar.isPresent());

		cInserted = oCar.get();
		assertEquals("Carro01", cInserted.getName());
		assertEquals("classicos", cInserted.getType());

		//deletar
		service.delete(id);

		//verificar se deletou
		assertFalse(service.findById(id).isPresent());

	}

	@Test
	public void testList() {
		List<CarDTO> cl = service.findAll();

		assertEquals(30,  cl.size());

	}

	@Test
	public void testGet(){
		Optional<CarDTO> op = service.findById(11L);

		assertTrue(op.isPresent());

		assertEquals("Ferrari FF", op.get().getName());
	}

	@Test
	public void testTipo() {
		assertEquals(10, service.findByType("classicos").size());
		assertEquals(10, service.findByType("esportivos").size());
		assertEquals(10, service.findByType("luxo").size());
		assertEquals(0, service.findByType("x").size());

	}

}
