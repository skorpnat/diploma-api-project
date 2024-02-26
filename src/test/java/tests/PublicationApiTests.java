package tests;


import io.qameta.allure.Feature;
import models.CreatePublicationResponseModel;
import models.PublicationResponseModel;
import models.PublicationSingleModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.PublicationSpecs.*;

public class PublicationApiTests {

    @Test
    @Feature("Получение данных по публикации")
    @DisplayName("Получение данных одной публикации")
    public void testGetSinglePost() {
        PublicationResponseModel response = step("GET запрос из раздела data про пользователя", () ->
                given(postRequestSpec)
                        .when()
                        .get("1")
                        .then()
                        .spec(successfulResponse)
                        .extract().as(PublicationResponseModel.class));

        step("Проверка полученных значений публикации", () -> {
            assertThat(response.getId()).isEqualTo(1);
            assertThat(response.getUserId()).isEqualTo(1);
            assertThat(response.getTitle()).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
            assertThat(response.getBody()).isEqualTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        });

    }

    @Test
    @Feature("Получение данных по публикации")
    @DisplayName("Запрос данных на не существующую публикацию")
    public void testGetNotExistingPost() {
        step("GET запрос данных не существующую публикацию", () -> {
            given(postRequestSpec)
                    .when()
                    .get("1055")
                    .then()
                    .spec(notFoundResponse);
        });
    }

    @Test
    @Feature("Удаление публикации")
    @DisplayName("Удаление публикации")
    public void testDeletePost() {
        step("DELETE запрос на удаление публикации и проверка кода ответа", () -> {
            given(postRequestSpec)
                    .delete("2")
                    .then()
                    .spec(successfulResponse);
        });
    }

    @Test
    @Feature("Новая публикация")
    @DisplayName("Создание новой публикации")
    public void testPostNewUser() {

        PublicationSingleModel singlePostBody = new PublicationSingleModel();
        singlePostBody.setUserId(1);
        singlePostBody.setBody("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nquia et suscipit");
        singlePostBody.setTitle("quia et suscipit");

        CreatePublicationResponseModel response = step("POST запрос для создания новой публикации", () ->
                given(postRequestSpec)
                        .body(singlePostBody)
                        .when()
                        .post("")
                        .then()
                        .spec(successfulCreateResponse)
                        .extract().as(CreatePublicationResponseModel.class));

        step("Проверка параметров созданной успешно публикации", () -> {
            assertThat(response.getId()).isNotNull();
            assertThat(response.getUserId()).isEqualTo(1);
            assertThat(response.getBody()).isEqualTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nquia et suscipit");
            assertThat(response.getTitle()).isEqualTo("quia et suscipit");
        });
    }

 @Test
 @Feature("Получение данных по публикации")
 @DisplayName("Проверка наличия публикации в списке")
 public void testGetListPost() {

     List<PublicationResponseModel> publications = step("Получение списка публикаций", () ->
                 given(postRequestSpec)
                     .when()
                     .get()
                     .then()
                     .spec(successfulResponse)
                     .extract().jsonPath().getList("", PublicationResponseModel.class));

     step("Проверка наличия публикации с определенным заголовком в списке", () ->
       assertThat(publications).extracting(PublicationResponseModel::getTitle).contains("ea molestias quasi exercitationem repellat qui ipsa sit aut"));

 }
}