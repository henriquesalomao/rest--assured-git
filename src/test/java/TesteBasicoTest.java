import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TesteBasicoTest {
    // faz a leitura de onde está o caminho do arquivo json de onde está a massa de teste
    public String lerJson(String caminhoArquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
    }


    // Define um método de teste
    @Test
    public void testGetBooking() {
        // Configura a URL base para as requisições da API
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Configura e executa a requisição GET para o endpoint "/booking/"
        given() // Define as configurações da requisição (headers, parâmetros, etc.)
                .header("Accept", "*/*") //adiciona o header accept
                .when() // Indica o início da execução da requisição
                .get("/booking/") // Especifica o endpoint a ser chamado
                .then() // Define as validações da resposta
                .statusCode(200) // Verifica se o status code da resposta é 200 (OK)
                .log().all();    // Loga no console todos os detalhes da resposta (body, headers, etc.)
    }
       // teste abaixo vai no id especifico e valida o corpo da chamada
    @Test
    public void testValidaBody() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        given()

                .header("Accept", "application/json")

                .when()

                .get("/booking/4855")

                .then()

                .statusCode(200)

                .body("firstname", equalTo("FÉ"))

                .body("lastname", equalTo("FISICA QUANTICA"))

                .body("totalprice", equalTo(111))

                .body("depositpaid", equalTo(true))

                .body("bookingdates.checkin", equalTo("2018-01-01"))

                .body("bookingdates.checkout", equalTo("2019-01-01"))

                .body("additionalneeds", equalTo("DEUS ME MANTEM DE PÉ"));

    }

    @Test
    public void testCadastrarReserva() throws IOException {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        //chama a leitura do arquivo Json
        String jsonBody = lerJson("src/test/resources/payloads/reserva.json");



        given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .post("/booking")
                .then()
                .statusCode(200)
                .body("booking.firstname", equalTo("ronaldo"))
                .body("booking.lastname", equalTo("palmeiras"))
                .body("booking.totalprice", equalTo(51))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2018-01-01"))
                .body("booking.bookingdates.checkout", equalTo("2019-01-01"))
                .body("booking.additionalneeds", equalTo("DEUS ME MANTEM DE PÉ"));





    }


}
