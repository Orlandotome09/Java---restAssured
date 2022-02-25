package desafioSicredi.Sicredi;


import org.junit.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class TestPostSimulacao {

	
	@Test
	public void postSimulacaoSucesso() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"asddn77787981a\", \"email\": \"test@test.com\", \"valor\": \"3000\", \"parcelas\": \"3\", \"seguro\": \"true\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("nome", is("test"))
			.body("cpf", is("asddn77787981a"))
			.body("email", is("test@test.com"))
			.body("valor", is(3000))
			.body("parcelas", is(3))
			.body("seguro", is(true))
		;
	}
	
	// bug, simulacao aceitando valor menor que 1.000 // valor da simulação que deve ser igual ou maior que R$ 1.000 e menor ou igual que R$ 40.000
	@Test
	public void valorMenorQue1000() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"8552220\", \"email\": \"test@test.com\", \"valor\": \"999\", \"parcelas\": \"3\", \"seguro\": \"true\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(201)
		;
	}
	// bug, simulacao aceitando valor maior que 40.000 // valor da simulação que deve ser igual ou maior que R$ 1.000 e menor ou igual que R$ 40.000
	@Test
	public void valorMaiorQue40000() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"a7778798oaa12\", \"email\": \"test@test.com\", \"valor\": \"40.001\", \"parcelas\": \"3\", \"seguro\": \"true\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(201)
		;
	}
	
	@Test
	public void parcelaMenorQue2() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"7778798oaac\", \"email\": \"test@test.com\", \"valor\": \"40.001\", \"parcelas\": \"1\", \"seguro\": \"true\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.parcelas", is("Parcelas deve ser igual ou maior que 2"))
		;
	}
	// bug, parcelas aceitando valor maior que 48 // número de parcelas para pagamento que deve ser igual ou maior que 2 e menor ou igual a 48 
	@Test
	public void parcelaMaiorQue48() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"7ad8798oaaacq1\", \"email\": \"test@test.com\", \"valor\": \"40.000\", \"parcelas\": \"49\", \"seguro\": \"true\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(201)
		;
	}
	
	@Test
	public void parcelaNaoAceitaString() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"7778a798oaacq1\", \"email\": \"test@test.com\", \"valor\": \"40.000\", \"parcelas\": \"tra\", \"seguro\": \"true\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(400)
		;
	}
	
	@Test
	public void valorNaoAceitaString() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"7778a798oaacqa1\", \"email\": \"test@test.com\", \"valor\": \"string\", \"parcelas\": \"45\", \"seguro\": \"true\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(400)
		;
	}
	
	// Nesse teste existe um bug de status e tambem de response, como podemos ver o status retornado é 400 e a mensagem ( erro ) nao confere CPF já existente.
	//Uma simulação para um mesmo CPF retorna um HTTP Status 409 com a mensagem "CPF já existente" 
	
	@Test
	public void postSimulacaoDuplicado() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"77787\", \"email\": \"test@test.com\", \"valor\": \"300\", \"parcelas\": \"3\", \"seguro\": \"true\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(400)
			.body("mensagem", is("CPF duplicado"))
		;
	}
	
	// Existe um bug relacionado ao swagger e esse teste, no Swagger é descrito que CPF e Nome sao obrigatorios, sendo que podemos observar no log que os dois campos nao sao retornados como required
	@Test
	public void semCampoNome() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"cpf\": \"k\", \"email\": \"as@hotmail.com\", \"valor\": \"200\", \"parcelas\": \"2\", \"seguro\": \"\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.nome", is("Nome não pode ser vazio"))
		;
	}
	
	@Test
	public void semCampoCpf() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"email\": \"as@hotmail.com\", \"valor\": \"200\", \"parcelas\": \"2\", \"seguro\": \"\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.cpf", is("CPF não pode ser vazio"))
		;
	}
	
	@Test
	public void semCampoEmail() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"k\", \"valor\": \"200\", \"parcelas\": \"2\", \"seguro\": \"\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.email", is("E-mail não deve ser vazio"))
		;
	}
	
	@Test
	public void semCampoValor() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"k\", \"email\": \"as@hotmail.com\", \"parcelas\": \"2\", \"seguro\": \"\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.valor", is("Valor não pode ser vazio"))
		;
	}
	
	@Test
	public void semCampoParcelas() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"k\", \"email\": \"as@hotmail.com\", \"valor\": \"200\", \"seguro\": \"\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.parcelas", is("Parcelas não pode ser vazio"))
		;
	}
	

	@Test
	public void semCampoSeguro() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"test\", \"cpf\": \"k\", \"email\": \"as@hotmail.com\", \"valor\": \"200\", \"parcelas\": \"2\"}")
		.when()
			.post("http://localhost:8080/api/v1/simulacoes")
		.then()
			.log().all()
			.statusCode(500)
		;
	}

}
