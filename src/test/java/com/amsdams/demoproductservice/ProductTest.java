package com.amsdams.demoproductservice;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ProductTest {
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(Product.class).withRedefinedSuperclass()
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS, Warning.INHERITED_DIRECTLY_FROM_OBJECT)
				.verify();
	}
}
