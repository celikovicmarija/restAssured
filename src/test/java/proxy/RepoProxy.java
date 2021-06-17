package proxy;

import common.Util;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Repository;

import java.net.URI;
import java.util.List;

public class RepoProxy {

    private static final URI NEW_REPO = URI.create("__files/oneRepo.json");
    public static RequestSpecification reqSpec;
    public static  void authorizeUser(){
       reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com/")
                .addHeader("accept", "application/vnd.github.v3+json")
                .addHeader("authorization", "Bearer " + Util.getPropertyValue("token"))
                .setContentType(ContentType.JSON).build();
    }


    public static List<Repository> getListOfRepositories() {
        //https://api.spotify.com/v1/users/{user_id}/playlists
        return given().spec(reqSpec)
                .get("users/" + Util.getPropertyValue("username") + "/repos").then()
                .extract().body()
                .jsonPath().getList(".", Repository.class);
    }
//Repository
    public static Response getSpecificRepository() {
        return given().spec(reqSpec)
                .get("repos/" + Util.getPropertyValue("username") + "/repoNameNo1").then()
                .extract().response();
                        //response().as(Repository.class);
    }

    public static Response createRepository() {
        //POST /user/repos
        String body = Util.loadFile(NEW_REPO).replace("#DESCRIPTION#", "My custom description no 1")
                .replace("#NAME#", "repoNameNo1");
        return given().spec(reqSpec)
                .body(body)
                .when()
                .post("user/repos").then()
                .extract().response();
    }

    public static Response deleteRepository() {
        //DELETE /repos/{owner}/{repo}
        return given().spec(reqSpec)
                .when()
                .delete("repos/" + Util.getPropertyValue("username") + "/repoNameUpdated")
                .then().extract().response();
    }

    public static Response updateRepository() {
        //PATCH /repos/{owner}/{repo}
        String body = Util.loadFile(NEW_REPO).replace("#DESCRIPTION#", "My updated custom description no 1")
                .replace("#NAME#", "repoNameUpdated");
        return given().spec(reqSpec)
                .body(body)
                .when()
                .patch("repos/" + Util.getPropertyValue("username") + "/repoNameNo1")
                .then().extract().response();
    }
}
