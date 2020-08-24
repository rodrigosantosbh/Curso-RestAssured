package br.ce.rodrigo.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {

	@Test
	public void testOlaMundo() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		System.out.println(response.getBody().asString().equals("Ola Mundo!"));
		System.out.println(response.statusCode() == 200);

		Assert.assertTrue("O statuscode deveria ser 200", response.statusCode() == 200);

		// throw new RuntimeException();
		// ValidatableResponse validacao = response.then();
		// validacao.statusCode(200);

		Assert.assertEquals(200, response.statusCode());
	}

	@Test
	public void deveConhecerOutrasFormasRestAssured() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);

		RestAssured.get("http://restapi.wcaquino.me/ola").then().statusCode(200);
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);
		
		given() // Pre condicoes
		.when() // acoes
			.get("http://restapi.wcaquino.me/ola")
		.then() // assertivas
			.statusCode(200);
	}
	
	@Test
	public void devoConhecerMatchersHamcrest() {
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(128, Matchers.is(128));
		Assert.assertThat(128, Matchers.isA(Integer.class));
		Assert.assertThat(128d, Matchers.isA(Double.class));
		Assert.assertThat(128d, Matchers.greaterThan(120d));
		Assert.assertThat(128d, Matchers.lessThan(130d));
		
		java.util.List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1, 3, 5, 7, 9));
		assertThat(impares, containsInAnyOrder(1, 5, 3, 9, 7));
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(1, 7));
		
		assertThat("Maria", is(not("Joao")));
		assertThat("Maria", not("Joao"));
		assertThat("Maria", anyOf(is("Maria"), is("Joao")));
		assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
	}
	
	@Test
	public void devoValidarBody() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
	}
}
