import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;

public class UserService {

    Properties prop = new Properties();
    String token;
    String profileid;


    @BeforeTest
            public void getData() throws IOException {

        FileInputStream fis = new FileInputStream("src/env.properties");
        prop.load(fis);
        //prop.getproperty("HOST");

        //private void getprofileid()
        //{
          //  this.profileid = Auth.profileid;
        //}
    }


    @Test
    public void Auth() {
        RestAssured.baseURI=prop.getProperty("HOST");
        RequestSpecification request = RestAssured.
                given().
                //param("parameter","auth/authenticate")
                //header("Accept-Encoding","identity").
                        header("Authorization",prop.getProperty("Authorization_Header")).
                        header("Content-Type",prop.getProperty("Content")).
                        header("User-Agent",prop.getProperty("User-Agent")).
                        when();
        Response response = request.post(Resources.geturlauthres());
        response.then().assertThat().statusCode(200).
                and().header("Content-Type","application/json;charset=UTF-8").
                and().body("email",equalTo(Assertions.assertrespemail())).
                and().body("name",equalTo(Assertions.assertrespname()));
        // Extract profile id from response
        String responseString = response.asString();
        System.out.println(responseString);
        JsonPath js = new JsonPath(responseString);
        profileid = js.get("profile_id");
        System.out.println("ProfileID = " + profileid);

        // Extract sessiontoken from response
        token = js.get("token");
        System.out.println("Token = " + token);
    }

    @Test
    public void Authcheck() {
        RestAssured.baseURI = prop.getProperty("HOST");
        RequestSpecification request = RestAssured.
                given().
                //param("parameter","auth/authenticate")
                //header("Accept-Encoding","identity").
                        header("Authorization",prop.getProperty("Authorization_Header")).
                        header("Content-Type",prop.getProperty("Content")).
                        header("User-Agent",prop.getProperty("User-Agent")).
                        when();
        Response response = request.get(Resources.geturlauthcheckres());
        response.then().assertThat().statusCode(200).
                and().header("Content-Type",equalTo(Assertions.assertrespcontenttype())).
                and().body("email",equalTo(Assertions.assertrespemail())).
                and().body("name",equalTo(Assertions.assertrespname())).
                and().extract().response();

        // Extract profile id from response
        String responseString = response.asString();
        System.out.println(responseString);
        JsonPath js = new JsonPath(responseString);
        profileid = js.get("profile_id");
        System.out.println("ProfileID = " + profileid);

        // Extract sessiontoken from response
        token = js.get("token");
        System.out.println("Token = " + token);

    }

    @Test
    public void GetProfile() {
        RestAssured.baseURI = prop.getProperty("HOST");
        RequestSpecification request = RestAssured.
                given().
                //param("parameter","auth/authenticate")
                //header("Accept-Encoding","identity").
                header("Authorization",prop.getProperty("Authorization_Header")).
                header("Content-Type", prop.getProperty("Content")).
                header("User-Agent",prop.getProperty("User-Agent")).
                when();
        Response responseone = request.get(Resources.geturlgetprofileres()+ profileid);
        responseone.then().assertThat().statusCode(200).
                and().header("Content-Type",equalTo(Assertions.assertrespcontenttype())).
                and().body("email",equalTo(Assertions.assertrespemail())).
                and().body("name",equalTo(Assertions.assertrespname())).
                and().body("id", comparesEqualTo(profileid)).
                and().extract().response();
        String responseStringval = responseone.asString();
        System.out.println(responseStringval);
        System.out.println("ProfileID = " + profileid);

    }

   @Test
    public void GetUserMain() {
        RestAssured.baseURI = prop.getProperty("HOST");
        RequestSpecification request = RestAssured.
                given().
                //param("parameter","auth/authenticate")
                //header("Accept-Encoding","identity").
                        header("Authorization",prop.getProperty("Authorization_Header")).
                        header("Content-Type", prop.getProperty("Content")).
                        header("User-Agent",prop.getProperty("User-Agent")).
                        when();
        Response responsetwo = request.get(Resources.geturlgetusermanres());
        responsetwo.then().assertThat().statusCode(200).
                and().header("Content-Type",equalTo(Assertions.assertrespcontenttype())).
                and().body("profile_uuid", comparesEqualTo(profileid)).
                and().extract().response();
        String responseStringvalue = responsetwo.asString();
        System.out.println(responseStringvalue);
       System.out.println("ProfileID = " + profileid);

    }

    @Test
    public void GetUsersMe() {
        RestAssured.baseURI = prop.getProperty("HOST");
        RequestSpecification request = RestAssured.
                given().
                //param("parameter","auth/authenticate")
                //header("Accept-Encoding","identity").
                        header("Authorization",prop.getProperty("Authorization_Header")).
                        header("Content-Type", prop.getProperty("Content")).
                        header("User-Agent",prop.getProperty("User-Agent")).
                        when();
        Response responsetwo = request.get(Resources.geturlgetusermeres());
        responsetwo.then().assertThat().statusCode(200).
                and().header("Content-Type",equalTo(Assertions.assertrespcontenttype())).
                and().body("main_profile.id", comparesEqualTo(profileid)).
                and().extract().response();
        String responseStringvalue = responsetwo.asString();
        System.out.println(responseStringvalue);
        System.out.println("ProfileID = " + profileid);

    }

    @Test
    public void PutProfile() {
        RestAssured.baseURI = prop.getProperty("HOST");
        RequestSpecification request = RestAssured.
                given().
                //param("parameter","auth/authenticate")
                //header("Accept-Encoding","identity").
                        header("Authorization",prop.getProperty("Authorization_Header")).
                        header("Content-Type", prop.getProperty("Content")).
                        header("User-Agent",prop.getProperty("User-Agent")).
                        body("{"+
                                "\"gender\":\"female\","+
                                "\"yob\":1975"+
                        "}").
                        when();
        Response responsetwo = request.put(Resources.geturlputprofileres()+profileid);
        responsetwo.then().assertThat().statusCode(200).
                and().header("Content-Type",equalTo(Assertions.assertrespcontenttype())).
                and().body("id", comparesEqualTo(profileid)).
                and().body("gender",equalTo("female")).
                and().body("year_of_birth",equalTo(1975)).
                and().extract().response();
        String responseStringvalue = responsetwo.asString();
        System.out.println(responseStringvalue);
        System.out.println("ProfileID = " + profileid);

    }

    @Test
    public void PutUsersMe() {
        RestAssured.baseURI = prop.getProperty("HOST");
        RequestSpecification request = RestAssured.
                given().
                //param("parameter","auth/authenticate")
                //header("Accept-Encoding","identity").
                        header("Authorization",prop.getProperty("Authorization_Header")).
                        header("Content-Type", prop.getProperty("Content")).
                        header("User-Agent",prop.getProperty("User-Agent")).
                        body("{"+
                                "\"year_of_birth\":1997,"+
                                "\"gender\":male"+
                                "}").
                        when();
        Response responsetwo = request.put(Resources.geturlputusersmeres());
        System.out.println(responsetwo);
        responsetwo.then().assertThat().statusCode(200).
                and().header("Content-Type",equalTo(Assertions.assertrespcontenttype())).
                and().body("id", comparesEqualTo(profileid)).
                and().body("gender",equalTo("female")).
                and().body("year_of_birth",equalTo(1975)).
                and().extract().response();
        String responseStringvalue = responsetwo.asString();
        System.out.println(responseStringvalue);

    }
}
