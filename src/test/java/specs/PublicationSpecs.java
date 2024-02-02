package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;

public class PublicationSpecs {
    public static RequestSpecification postRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(ContentType.JSON)
            .baseUri("https://jsonplaceholder.typicode.com")
            .basePath("/posts");

    public static ResponseSpecification successfulResponse = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification successfulCreateResponse = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification notFoundResponse = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(ALL)
            .build();

}
