import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Objects;

public class rest_ctrl {
    public static void main(String[] args) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String url = "http://94.198.50.185:7081/api/users";
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, String.class);
        HttpHeaders headers2 =  new HttpHeaders();
        headers2.set("JSESSIONID", Objects.requireNonNull(response.getHeaders().get("Set-Cookie")).toString().substring(12,43));
        System.out.println(headers2);
        headers2.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(response);
        System.out.println(headers2);
        User user = new User();
        user.setId(3L);
        user.setAge((byte) 15);
        user.setName("James");
        user.setLastName("Brown");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);
        JSONObject jsonObject = new JSONObject(user);

        HttpEntity<String> requestEntity2 = new HttpEntity<>(jsonObject.toString(),headers2);
        ResponseEntity<String> response2 = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity2, String.class);
        System.out.println(response2);

        user.setName("Thomas");
        user.setLastName("Shelby");
        jsonObject = new JSONObject(user);
        HttpHeaders headers3 =  new HttpHeaders();
        headers3.set("Set-Cookie", Objects.requireNonNull(response2.getHeaders().get("Set-Cookie")).toString());
        headers3.setContentType(MediaType.APPLICATION_JSON);

        System.out.println(headers3);
        System.out.println(jsonObject);

        HttpEntity<String> requestEntity3 = new HttpEntity<>(jsonObject.toString(),headers2);
        ResponseEntity<String> response3 = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity3, String.class);
        System.out.println(response3);

        String delUrl= "http://94.198.50.185:7081/api/users/3";

        HttpHeaders headers4 =  new HttpHeaders();
        headers4.set("JSESSIONID", Objects.requireNonNull(response.getHeaders().get("Set-Cookie")).toString().substring(12,43));

        HttpEntity<String> requestEntity4 = new HttpEntity<>(null,headers2);
        ResponseEntity<String> response4 = restTemplate.exchange(
                delUrl, HttpMethod.DELETE, requestEntity4, String.class);
        System.out.println(response4);


//        URL url = new URL("http://94.198.50.185:7081/api/users");
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        final StringBuilder content = new StringBuilder();
//        while ((inputLine = in.readLine()) != null) {
//            content.append(inputLine);
//            String cookie = con.getHeaderField("Set-Cookie").split(";")[0];
//        }
//
//        System.out.println(content);
//        //System.out.println(cookie);
    }

    public static class  User {
        private Long id;
        private String name;
        private String lastName;
        private Byte age;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Byte getAge() {
            return age;
        }

        public void setAge(Byte age) {
            this.age = age;
        }
    }
}


