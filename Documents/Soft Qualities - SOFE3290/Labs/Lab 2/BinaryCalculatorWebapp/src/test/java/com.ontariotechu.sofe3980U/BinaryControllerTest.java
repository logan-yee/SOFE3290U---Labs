package com.ontariotechu.sofe3980U;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {

	@Autowired
	private MockMvc mvc;

	// GET calculator (existing + 3 more scenarios)

	@Test
	public void getDefault() throws Exception {
		this.mvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
	}

	@Test
	public void getParameter() throws Exception {
		this.mvc.perform(get("/").param("operand1", "111"))
			.andExpect(status().isOk())
			.andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "111"))
			.andExpect(model().attribute("operand1Focused", true));
	}

	@Test
	public void getParameterWithLeadingZeros() throws Exception {
		this.mvc.perform(get("/").param("operand1", "00101"))
			.andExpect(status().isOk())
			.andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "00101"))
			.andExpect(model().attribute("operand1Focused", true));
	}

	@Test
	public void getParameterEmptyOperand1() throws Exception {
		this.mvc.perform(get("/").param("operand1", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
	}

	// POST Add

	@Test
	public void postAdd() throws Exception {
		this.mvc.perform(post("/").param("operand1", "111").param("operator", "+").param("operand2", "111"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1110"))
			.andExpect(model().attribute("operand1", "111"));
	}

	@Test
	public void postAddZeros() throws Exception {
		this.mvc.perform(post("/").param("operand1", "0").param("operator", "+").param("operand2", "0"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "0"));
	}

	@Test
	public void postAddWithEmptyOperands() throws Exception {
		this.mvc.perform(post("/").param("operand1", "").param("operator", "+").param("operand2", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "0"));
	}

	// POST Multiply

	@Test
	public void postMultiply() throws Exception {
		this.mvc.perform(post("/").param("operand1", "11").param("operator", "*").param("operand2", "11"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1001"))
			.andExpect(model().attribute("operand1", "11"));
	}

	@Test
	public void postMultiplyByZero() throws Exception {
		this.mvc.perform(post("/").param("operand1", "111").param("operator", "*").param("operand2", "0"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "0"));
	}

	@Test
	public void postMultiplyZeroByAny() throws Exception {
		this.mvc.perform(post("/").param("operand1", "0").param("operator", "*").param("operand2", "1010"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "0"));
	}

	@Test
	public void postMultiplyOnes() throws Exception {
		this.mvc.perform(post("/").param("operand1", "1").param("operator", "*").param("operand2", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1"));
	}

	@Test
	public void postMultiplyDifferentLengths() throws Exception {
		this.mvc.perform(post("/").param("operand1", "101").param("operator", "*").param("operand2", "11"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1111"));
	}

	// POST AND

	@Test
	public void postAnd() throws Exception {
		this.mvc.perform(post("/").param("operand1", "111").param("operator", "&").param("operand2", "101"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "101"))
			.andExpect(model().attribute("operand1", "111"));
	}

	@Test
	public void postAndWithZero() throws Exception {
		this.mvc.perform(post("/").param("operand1", "111").param("operator", "&").param("operand2", "0"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "0"));
	}

	@Test
	public void postAndDifferentLengths() throws Exception {
		this.mvc.perform(post("/").param("operand1", "1010").param("operator", "&").param("operand2", "11"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "10"));
	}

	@Test
	public void postAndAllOnes() throws Exception {
		this.mvc.perform(post("/").param("operand1", "111").param("operator", "&").param("operand2", "111"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "111"));
	}

	// POST OR

	@Test
	public void postOr() throws Exception {
		this.mvc.perform(post("/").param("operand1", "110").param("operator", "|").param("operand2", "101"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "111"))
			.andExpect(model().attribute("operand1", "110"));
	}

	@Test
	public void postOrWithZero() throws Exception {
		this.mvc.perform(post("/").param("operand1", "101").param("operator", "|").param("operand2", "0"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "101"));
	}

	@Test
	public void postOrDifferentLengths() throws Exception {
		this.mvc.perform(post("/").param("operand1", "1").param("operator", "|").param("operand2", "100"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "101"));
	}

	@Test
	public void postOrBothZeros() throws Exception {
		this.mvc.perform(post("/").param("operand1", "0").param("operator", "|").param("operand2", "0"))
			.andExpect(status().isOk())
			.andExpect(view().name("result"))
			.andExpect(model().attribute("result", "0"));
	}

	// Unknown operator -> error view

	@Test
	public void postUnknownOperator() throws Exception {
		this.mvc.perform(post("/").param("operand1", "1").param("operator", "x").param("operand2", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("error"));
	}
}
