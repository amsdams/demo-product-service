package com.amsdams.demoproductservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

	private static final String RS_ID = "id";
	private static final String RS_NAME = "name";
	private static final String RS_VERSION = "version";
	private static final String RS_QUANTITY = "quantity";
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	public ProductRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
		this.jdbcTemplate = jdbcTemplate;

		// Build a SimpleJdbcInsert object from the specified data source
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("products")
				.usingGeneratedKeyColumns(RS_ID);
	}

	@Override
	public Optional<Product> findById(Integer id) {
		try {
			Product product = jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?", new Object[] { id },
					(rs, rowNum) -> {
						Product p = new Product();
						p.setId(rs.getInt(RS_ID));
						p.setName(rs.getString(RS_NAME));
						p.setQuantity(rs.getInt(RS_QUANTITY));
						p.setVersion(rs.getInt(RS_VERSION));
						return p;
					});
			return Optional.of(product);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Product> findAll() {
		return jdbcTemplate.query("SELECT * FROM products", (rs, rowNumber) -> {
			Product product = new Product();
			product.setId(rs.getInt(RS_ID));
			product.setName(rs.getString(RS_NAME));
			product.setQuantity(rs.getInt(RS_QUANTITY));
			product.setVersion(rs.getInt(RS_VERSION));
			return product;
		});
	}

	@Override
	public boolean update(Product product) {
		return jdbcTemplate.update("UPDATE products SET name = ?, quantity = ?, version = ? WHERE id = ?",
				product.getName(), product.getQuantity(), product.getVersion(), product.getId()) == 1;
	}

	@Override
	public Product save(Product product) {
		// Build the product parameters we want to save
		Map<String, Object> parameters = new HashMap<>(1);
		parameters.put(RS_NAME, product.getName());
		parameters.put(RS_QUANTITY, product.getQuantity());
		parameters.put(RS_VERSION, product.getVersion());

		// Execute the query and get the generated key
		Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);

		log.info("Inserting product into database, generated key is: {}", newId);

		// Update the product's ID with the new key
		product.setId((Integer) newId);

		// Return the complete product
		return product;
	}

	@Override
	public boolean delete(Integer id) {
		return jdbcTemplate.update("DELETE FROM products WHERE id = ?", id) == 1;
	}
}