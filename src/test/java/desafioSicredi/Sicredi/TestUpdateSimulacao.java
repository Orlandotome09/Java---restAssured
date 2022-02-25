package desafioSicredi.Sicredi;


import org.junit.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class TestUpdateSimulacao {

	//bug, api nao aceita fazer alteração no campo valor ( A alteração pode ser feita em qualquer atributo da simulação  )
	@Test
	public void putSimulacaoTodosOsCampos() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test2\", \"cpf\": \"n77787981a1a1\", \"email\": \"test@test1.com\", \"valor\": \"6000\", \"parcelas\": \"14\", \"seguro\": \"true\"}")
		.when()
			.put("http://localhost:8080/api/v1/simulacoes/n77787981a1a1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(113))
			.body("nome", is("test2"))
			.body("cpf", is("n77787981a1a1"))
			.body("email", is("test@test1.com"))
			.body("valor", is(5000))  // aqui
			.body("parcelas", is(14))
		;
	}
	
	
	@Test
	public void putSimulacaoSucess() {
		given()
			
			.contentType("application/json")
			.body("{\"nome\": \"test2\", \"cpf\": \"n77787981a1a1\", \"email\": \"test@test1.com\", \"valor\": \"5000.00\", \"parcelas\": \"14\", \"seguro\": \"true\"}")
		.when()
			.put("http://localhost:8080/api/v1/simulacoes/n77787981a1a1")
		.then()
			
			.statusCode(200)
			.body("id", is(113))
			.body("nome", is("test2"))
			.body("cpf", is("n77787981a1a1"))
			.body("email", is("test@test1.com"))
			.body("parcelas", is(14))
		;
	}
	
	
	@Test
	public void putSimulacaoNaoExiste() {
		given()
			.contentType("application/json")
			.body("{\"nome\": \"test2\", \"cpf\": \"n77787981a1a1\", \"email\": \"test@test1.com\", \"valor\": \"5000.00\", \"parcelas\": \"14\", \"seguro\": \"true\"}")
		.when()
			.put("http://localhost:8080/api/v1/simulacoes/9999999")
		.then()
			.statusCode(404)
			.body("mensagem", is("CPF 9999999 não encontrado"))

		;
	}

}
