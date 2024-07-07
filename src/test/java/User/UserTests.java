package User;

import Entities.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;

//Permite setar a ordem dos testes a partir da tag de ordem
//As tags que não possuem essa tag são executadas de forma sem prioridade
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTests {

    private static User user;

    public static Faker faker;

    public static RequestSpecification request;

    @BeforeAll

        public static void setup(){
            RestAssured.baseURI = "https://petstore.swagger.io/v2";

            faker = new Faker();

            //Será executado antes de todos os testes
            // Responsável por setar as informações do usuário
            user = new User(faker.name().username(),
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.internet().safeEmailAddress(),
                    faker.internet().password(8,10),
                    faker.phoneNumber().toString());
    }

    // Setar uma requisição antes de cada teste
    @BeforeEach
     void setRequest(){
        request = given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .header("api-key", "special-key")
                .contentType(ContentType.JSON);


    }

    @Test
    public void CreateNewUser_WithValidData_ReturnOk(){

    }

}

