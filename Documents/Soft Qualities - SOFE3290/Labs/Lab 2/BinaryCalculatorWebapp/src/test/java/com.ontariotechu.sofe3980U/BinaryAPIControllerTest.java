package com.ontariotechu.sofe3980U;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

	@Autowired
	private MockMvc mvc;

	// Add (plain and JSON)

	@Test
	public void add() throws Exception {
		this.mvc.perform(get("/add").param("operand1", "111").param("operand2", "1010"))
			.andExpect(status().isOk())
			.andExpect(content().string("10001"));
	}

	@Test
	public void addJson() throws Exception {
		this.mvc.perform(get("/add_json").param("operand1", "111").param("operand2", "1010"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
	}

	@Test
	public void addZeros() throws Exception {
		this.mvc.perform(get("/add").param("operand1", "0").param("operand2", "0"))
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}

	@Test
	public void addEmptyOperands() throws Exception {
		this.mvc.perform(get("/add").param("operand1", "").param("operand2", ""))
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}

	// Multiply (plain and JSON)

	@Test
	public void multiply() throws Exception {
		this.mvc.perform(get("/multiply").param("operand1", "11").param("operand2", "11"))
			.andExpect(status().isOk())
			.andExpect(content().string("1001"));
	}

	@Test
	public void multiplyJson() throws Exception {
		this.mvc.perform(get("/multiply_json").param("operand1", "11").param("operand2", "11"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1001));
	}

	@Test
	public void multiplyByZero() throws Exception {
		this.mvc.perform(get("/multiply").param("operand1", "111").param("operand2", "0"))
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}

	@Test
	public void multiplyZeroByAny() throws Exception {
		this.mvc.perform(get("/multiply").param("operand1", "0").param("operand2", "1010"))
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}

	@Test
	public void multiplyOnes() throws Exception {
		this.mvc.perform(get("/multiply").param("operand1", "1").param("operand2", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("1"));
	}

	@Test
	public void multiplyDifferentLengths() throws Exception {
		this.mvc.perform(get("/multiply").param("operand1", "101").param("operand2", "11"))
			.andExpect(status().isOk())
			.andExpect(content().string("1111"));
	}

	// AND (plain and JSON)

	@Test
	public void and() throws Exception {
		this.mvc.perform(get("/and").param("operand1", "111").param("operand2", "101"))
			.andExpect(status().isOk())
			.andExpect(content().string("101"));
	}

	@Test
	public void andJson() throws Exception {
		this.mvc.perform(get("/and_json").param("operand1", "111").param("operand2", "101"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("and"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(101));
	}

	@Test
	public void andWithZero() throws Exception {
		this.mvc.perform(get("/and").param("operand1", "111").param("operand2", "0"))
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}

	@Test
	public void andDifferentLengths() throws Exception {
		this.mvc.perform(get("/and").param("operand1", "1010").param("operand2", "11"))
			.andExpect(status().isOk())
			.andExpect(content().string("10"));
	}

	@Test
	public void andAllOnes() throws Exception {
		this.mvc.perform(get("/and").param("operand1", "111").param("operand2", "111"))
			.andExpect(status().isOk())
			.andExpect(content().string("111"));
	}

	// OR (plain and JSON)

	@Test
	public void or() throws Exception {
		this.mvc.perform(get("/or").param("operand1", "110").param("operand2", "101"))
			.andExpect(status().isOk())
			.andExpect(content().string("111"));
	}

	@Test
	public void orJson() throws Exception {
		this.mvc.perform(get("/or_json").param("operand1", "110").param("operand2", "101"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("or"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(111));
	}

	@Test
	public void orWithZero() throws Exception {
		this.mvc.perform(get("/or").param("operand1", "101").param("operand2", "0"))
			.andExpect(status().isOk())
			.andExpect(content().string("101"));
	}

	@Test
	public void orDifferentLengths() throws Exception {
		this.mvc.perform(get("/or").param("operand1", "1").param("operand2", "100"))
			.andExpect(status().isOk())
			.andExpect(content().string("101"));
	}

	@Test
	public void orBothZeros() throws Exception {
		this.mvc.perform(get("/or").param("operand1", "0").param("operand2", "0"))
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}

	@Test
	public void addSingleBitOperands() throws Exception {
		this.mvc.perform(get("/add").param("operand1", "1").param("operand2", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string("10"));
	}
}
