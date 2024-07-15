package Pets;

import Entities.Pet;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.path.json.JSONAssertion;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

//Permite setar a ordem dos testes a partir da tag de ordem
//As tags que não possuem essa tag são executadas de forma sem prioridade
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class PetTests {

    private static Pet pet;

    public static Faker faker;

    public static RequestSpecification request;

    @BeforeAll

    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        faker = new Faker();

        //Será executado antes de todos os testes
        // Responsável por setar as informações do usuário aleatoriamente
        pet = new Pet(faker.funnyName());

    }

    // Setar uma requisição antes de cada teste
    @BeforeEach
    void setRequest() {
        request = given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .header("api-key", "special-key")
                .contentType(ContentType.JSON);


    }
    @Test
    @Order(1)
    public void AddNewPetInStore(){

        request
                .body(pet)
                .when()
                .post("/pet/")
                .then()
                .assertThat().statusCode(200);


    }
    @Test
    @Order(2)
    public void UpdatePet(){

        request
                .body(pet)
                .when()
                .put("/pet/")
                .then()
                .assertThat().statusCode(200)
                .and().assertThat().body("code", equalTo(pet.getId()))
                .log().all();


    }

    @Test
    @Order(3)
    public void FindPet_ByStatus(){

        request
                .body(pet)
                .when()
                //.get("/pet/findByStatus?status=available&status=available")
                .get("/pet/findByStatus?status=available&status="+pet.getStatus())
                .then().assertThat().statusCode(200)
                .log().all();

    }

    @Test
    @Order(4)
    public void DeletePet(){

        request
                .param("name", pet.getName())
                .log().all()
                .when()
                .delete("/pet/"+pet.getId())
                .body().print();

    }
}