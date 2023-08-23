package restAssured.patient;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;//* means here we have all methods
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class retrievePatientsInfo {

    // Happy Path
    @Test
    public void ValidateStatusCode() {

        given().
                baseUri("https://apimgmt-dev-crisp.azure-api.net").
                queryParam("Id", "111").
                when().
                get("/patients/query").
                then().
                log().all().

                assertThat().

                statusCode(200);

    }

    @Test
    public void ValidateIdAddressNameDOBandResponsetime() {
        Response res = given().
                baseUri("https://apimgmt-dev-crisp.azure-api.net").
                queryParam("Id", "111").
                when().
                get("/patients/query").
                then().
                //log().all().

                        assertThat().

                statusCode(200).
                extract().response();
        JsonPath jp = res.jsonPath();
        String JennId = jp.get("[0].Id");
        System.out.println(JennId);
        Assert.assertEquals(JennId, "111");
        String JennFullname = jp.get("[0].Name");
        System.out.println(JennFullname);
        Assert.assertEquals(JennFullname, "Jenn D.");
        String JennDOB = jp.get("[0].DateOfBirth");
        System.out.println(JennDOB);
        Assert.assertEquals(JennDOB, "1934-06-01");

        String JennAddress = jp.get("[0].Address");
        System.out.println(JennAddress);
        Assert.assertEquals(JennAddress, "CA");

        long responsetime = res.getTime();
        Assert.assertEquals(responsetime, 180);
    }


    // Unhappy path
    @Test
    public void ValidateInvalidNameQueryParameter() {
        Response res = given().
                baseUri("https://apimgmt-dev-crisp.azure-api.net").
                queryParam("Name", "Jenn").
                when().
                get("/patients/query").
                then().
                log().all().

                assertThat().

                statusCode(200).extract().response();
        JsonPath jp = res.jsonPath();
        String  name = jp.get("[0].name");
        if (name == null) {
            System.out.println("it is null");
        }
        else {
            System.out.println("not null");
        }


    }
    @Test
    public void ValidateInvalidAddressQueryParameter() {
        Response res = given().
                baseUri("https://apimgmt-dev-crisp.azure-api.net").
                queryParam("Address", "Valley").
                when().
                get("/patients/query").
                then().
                log().all().

                assertThat().

                statusCode(200).extract().response();
        JsonPath jp = res.jsonPath();
        String  address = jp.get("[0].Address");
        if (address == null) {
            System.out.println("it is null");
        }
        else {
            System.out.println("not null");
        }


    }
    @Test
    public void ValidateInvalidDOBQueryParameter() {
        Response res = given().
                baseUri("https://apimgmt-dev-crisp.azure-api.net").
                queryParam("DateOfBirth", "2000-03-01").
                when().
                get("/patients/query").
                then().
                log().all().

                assertThat().

                statusCode(200).extract().response();
        JsonPath jp = res.jsonPath();
        String  dob = jp.get("[0].DateOfBirth");
        if (dob == null) {
            System.out.println("it is null");
        }
        else {
            System.out.println("not null");
        }


    }
}