package desafioSicredi.Sicredi;

import org.junit.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class TestGetRestricoes {


	@Test
	public void cpfComRestricao() {
		given()
		.contentType("application/json")
		.when()
			.get("http://localhost:8080/api/v1/restricoes/97093236014")
		.then()
		.body("mensagem", containsString("O CPF 97093236014 tem problema")).statusCode(200);
		
	}	
	
	@Test
	public void cpfSemRestricao() {
		given()
			.contentType("application/json")
			.when()
				.get("http://localhost:8080/api/v1/restricoes/9709")
			.then().statusCode(204);		
	}	
	



}
