package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CarroServiceTests {

	@Autowired
	private CarroService service;

	@Test
	public void testInsert() {

		Carro c = new Carro();
		c.setNome("Carro01");
		c.setTipo("classicos");

		CarroDTO cInserted = service.insert(c);
		assertNotNull(cInserted);

		Long id = cInserted.getId();
		assertNotNull(id);

		//buscar obj
		Optional<CarroDTO> oCar = service.getById(id);
		assertTrue(oCar.isPresent());

		cInserted = oCar.get();
		assertEquals("Carro01", cInserted.getNome());
		assertEquals("classicos", cInserted.getTipo());

		//deletar
		service.delete(id);

		//verificar se deletou
		assertFalse(service.getById(id).isPresent());

	}

	@Test
	public void testList() {
		List<CarroDTO> cl = service.getCarros();

		assertEquals(30,  cl.size());

	}

	@Test
	public void testGet(){
		Optional<CarroDTO> op = service.getById(11L);

		assertTrue(op.isPresent());

		assertEquals("Ferrari FF", op.get().getNome());
	}

	@Test
	public void testTipo() {
		assertEquals(10, service.getByTipo("classicos").size());
		assertEquals(10, service.getByTipo("esportivos").size());
		assertEquals(10, service.getByTipo("luxo").size());
		assertEquals(0, service.getByTipo("x").size());

	}

}
