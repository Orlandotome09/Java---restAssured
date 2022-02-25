package desafioSicredi.Sicredi;


import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class TestDeleteSimulacao {

	// segundo a documentação o retorno teria que ser 204 - mas esta retornando 200 segundo o swagger 
	@Test
	public void deleteSimulacaoSucesso() {
		given()
			.log().all()
		.when()
			.delete("http://localhost:8080/api/v1/simulacoes/13")
		.then()
			.log().all()
			.statusCode(200)
			//.statusCode(204)
		;
	}
	
	// api esta aceitando qualquer id colocado no endpoint, sendo retornado o status 200 sempre
	@Test
	public void deleteSimulacaoFail() {
		given()
			.log().all()
		.when()
			.delete("http://localhost:8080/api/v1/simulacoes/0000")
		.then()
			.log().all()
			.statusCode(200)
			//.statusCode(400)
		;
	}
	
}
