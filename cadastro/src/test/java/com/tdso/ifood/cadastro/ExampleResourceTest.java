package com.tdso.ifood.cadastro;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
public class ExampleResourceTest {

    @Test
    @DataSet("exemplo-test.yml")
    public void testHelloEndpoint() {
        String resultado = given().when().get("/restaurantes").then().statusCode(200).extract().asString();
        System.out.println(resultado);
    }

}