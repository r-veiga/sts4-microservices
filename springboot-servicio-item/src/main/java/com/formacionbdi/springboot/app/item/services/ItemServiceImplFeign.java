package com.formacionbdi.springboot.app.item.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.item.clientes.ProductoClienteRest;
import com.formacionbdi.springboot.app.item.models.Item;

@Service
@Primary
public class ItemServiceImplFeign implements ItemService {

	private static final int NUM_ARTICULOS = 2;
	
	@Autowired
	private ProductoClienteRest clienteFeign;
	
	@Override
	public List<Item> findAll() {
		return clienteFeign
					.listar()
					.stream()
					.map(p -> new Item(p, NUM_ARTICULOS))
					.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item( clienteFeign.detalle(id), cantidad );
	}

}
