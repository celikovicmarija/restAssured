package stepdefs;

import common.Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.response.Response;
import model.Repository;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import proxy.RepoProxy;

import java.util.List;


public class StepImplementations {
    private Repository repo;
    private List<Repository> repositories;
    private Response response;

    @Given("the user is authorized")
    public void the_user_is_authorized() {
        RepoProxy.authorizeUser();
    }

    @When("user asks for specific repository")
    public void user_asks_for_specific_repository() {
        response = RepoProxy.getSpecificRepository();
    }

    @When("user asks for list of repositories")
    public void user_asks_for_list_of_repositories() {
        repositories = RepoProxy.getListOfRepositories();
    }

    @When("user creates a new repository")
    public void users_creates_a_new_repository() {
        response = RepoProxy.createRepository();
    }

    @When("user updates a repository")
    public void user_updates_a_repository() {
        response = RepoProxy.updateRepository();
    }

    @When("user deletes a repository")
    public void user_deletes_a_repository() {
        response = RepoProxy.deleteRepository();
    }

    @Then("the list of repositories will be shown")
    public void the_list_of_repositories_will_be_shown() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(repositories).isNotNull();
        });
    }

    @Then("the new repository will be created")
    public void the_new_repository_will_be_created() {
        Assertions.assertThat(response.statusLine()).isEqualTo("HTTP/1.1 201 Created");
        String repo_name = Util.getJsonPath(response, "name");
        Assertions.assertThat(repo_name).isEqualTo("repoNameNo1");
    }

    @Then("the repository will be shown")
    public void the_repository_will_be_shown() {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("repositoryJsonSchema.json"));
        repo=response.body().as(Repository.class);
        Assertions.assertThat(repo).isNotNull();
        Assertions.assertThat(repo.getName()).isEqualTo("repoNameUpdated");


    }

    @Then("the repository will be updated")
    public void repository_will_be_updated() {
        SoftAssertions.assertSoftly(softly -> {
            Assertions.assertThat(response.statusLine()).isEqualTo("HTTP/1.1 200 OK");
            Assertions.assertThat(response.body().as(Repository.class).getName()).isEqualTo("repoNameUpdated");
        });
    }

    @Then("the repository is deleted")
    public void the_repository_is_deleted() {
        Assertions.assertThat(response.statusLine()).isEqualTo("HTTP/1.1 204 No Content");
    }

}
