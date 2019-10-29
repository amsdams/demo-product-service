package com.amsdams.demoproductservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	public Product(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public Product(int id, String name, int quantity) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}

	private Integer id;
	private String name;
	private Integer quantity;
	private Integer version;

}
