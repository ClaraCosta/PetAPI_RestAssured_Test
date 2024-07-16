package User;

import Entities.User;
import org.junit.jupiter.api.*;
import com.github.javafaker.Faker;
import io.restassured.http.Header;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import static io.restassured.config.LogConfig.logConfig;
import io.restassured.specification.RequestSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


//=================================================================================================================
//Setando a ordem dos testes a partir da tag de ordem
//
//As tags que não possuem essa tag são executadas de forma sem prioridade
//=================================================================================================================

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

//==================================================================================================================
//                                                | USER TESTS |
//==================================================================================================================


public class UserTests {

    private static User user;

    public static Faker faker;

    public static RequestSpecification request;

    @BeforeAll

        public static void setup(){
            RestAssured.baseURI = "https://petstore.swagger.io/v2";

            faker = new Faker();


            // Responsável por gerar aleatoriamente informações do USER
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
    public void CreateNewUser(){

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


//==================================================================================================================
//                                          | TESTES SEQUENCIAIS |
//==================================================================================================================


    @Test
    @Order(2)
    public void UpdateUser(){

        request
                .body(user)
                .when()
                .put("/user/:username")
                .then()
                .assertThat().statusCode(200).and()
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", isA(String.class))
                .body("size()", equalTo(3));


    }


    @Test
    @Order(3)
    public void FindLogin(){
        request
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .when()
                .get("/user/login")
                .then()
                .assertThat()
                .statusCode(200)
                .and().time(lessThan(2000L));
    }

    public void GetUserByUsername(){

        request
                .when()
                .get("/user/" + user.getUsername())
                .then()
                .assertThat().statusCode(200).and().time(lessThan(2000L))
                .and().body("firstName", equalTo(user.getFirstName()));

    }

    @Test
    @Order(4)
    public void DeleteUser(){

        request
                .when()
                .delete("/user/" + user.getUsername())
                .then()
                .assertThat().statusCode(200).and().time(lessThan(2000L))
                .log();

    }



}

