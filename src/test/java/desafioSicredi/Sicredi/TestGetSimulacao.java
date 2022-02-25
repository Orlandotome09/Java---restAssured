package desafioSicredi.Sicredi;


import org.junit.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class TestGetSimulacao {

	
	@Test
	public void getSimulacaoTodas() {
		given()
			.log().all()
		.when()
			.get("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(200);
	}
	
	
	@Test
	public void getSimulacao() {
		given()
			.log().all()
		.when()
			.get("http://localhost:8080/api/v1/simulacoes/a7778798oaa")
		.then()
			.log().all()
			.statusCode(200)
			.body("cpf", containsString("a7778798oaa"))
			;
	}
	
	
	@Test
	public void getSimulacaoFail() {
		given()
			.log().all()
		.when()
			.get("http://localhost:8080/api/v1/simulacoes/123456")
		.then()
			.log().all()
			.statusCode(404)
			.body("mensagem", containsString("CPF 123456 não encontrado"))
			;
	}


}
