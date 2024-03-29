package com.formacionbdi.springboot.app.item.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

	private final static int NUM_UNIDADES = 2;
	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() { 
		List<Producto> productos;
		productos = Arrays.asList(
						clienteRest
							.getForObject(	
								"http://servicio-productos/listar", 
								Producto[].class
							)
						); 
		return productos
				.stream()
				.map(p -> new Item(p, NUM_UNIDADES))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		Producto producto = clienteRest
								.getForObject(
									"http://servicio-productos/ver/{id}", 
									Producto.class, 
									pathVariables
								);
		return new Item(producto, cantidad);
	}

}
