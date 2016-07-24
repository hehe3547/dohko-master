package com.dohko.core.base;

import java.math.BigDecimal;

import org.junit.Test;


public class TestDataset {

	@Test
	public void testDataset() {
		DataMap props = DataMap.Builder.create();
		props.put("intKey", 1)
			.put("doubleKey", 2.342234)
			.put("bigDecimalKey", new BigDecimal(12345678.45))
			.put("stringToBig", "1273.234");
		org.junit.Assert.assertEquals(1, props.getIntValue("intKey"));
		org.junit.Assert.assertEquals(1, props.getIntValue("intKey"));
		org.junit.Assert.assertEquals(2.342234, props.getDoubleValue("doubleKey"), 0.01);
		org.junit.Assert.assertEquals(new BigDecimal(12345678.45), props.getBigDecimal("bigDecimalKey"));
		org.junit.Assert.assertEquals(new BigDecimal("1273.234"), props.getBigDecimal("stringToBig"));
		org.junit.Assert.assertEquals(-1273.234, props.getDoubleValue("stringToBig") * -1, 0.001);

	}
}
