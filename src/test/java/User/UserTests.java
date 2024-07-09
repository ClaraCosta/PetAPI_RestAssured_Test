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
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

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
    @Order(1)
    public void CreateNewUser_WithValidData_ReturnOk(){

        request
                .body(user)
                .when()
                .post("/user")
                .then()
                .assertThat().statusCode(200).and()
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", isA(String.class))
                .body("size()", equalTo(3));


    }


    @Test
    @Order(2)
    public void GetLogin_ValidUser_ReturnOk(){
        request
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .when()
                .get("/user/login")
                .then()
                .assertThat()
                .statusCode(200)
                .and().time(lessThan(2000L))
                .and().body(matchesJsonSchemaInClasspath("loginResponseSchema.json"));
    }

    public void GetUserByUsername_userIsValid_return200(){

        request
                .when()
                .get("/user/" + user.getUsername())
                .then()
                .assertThat().statusCode(200).and().time(lessThan(2000L))
                .and().body("firstName", equalTo(user.getFirstName()));

        // TO DO: Schema Validation
    }

    @Test
    @Order(3)
    public void DeleteUser_UserExists_Return200(){

        request
                .when()
                .delete("/user/" + user.getUsername())
                .then()
                .assertThat().statusCode(200).and().time(lessThan(2000L))
                .log();

    }

}

