package com.terradotta.terradotta_proxy.controller;

import com.terradotta.terradotta_proxy.model.ResponseMessage;
import com.terradotta.terradotta_proxy.model.Terradotta;
import com.terradotta.terradotta_proxy.model.authenticationRequest;
import com.terradotta.terradotta_proxy.model.authenticationResponse;
import com.terradotta.terradotta_proxy.services.MyUserDetailsService;
import com.terradotta.terradotta_proxy.util.jwtutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
public class terradotaController {
    @Value("${access.url}")
    private String BASE_URL;
    private final RestTemplate restTemplate;
    Logger logger = LoggerFactory.getLogger(terradotaController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private jwtutil jwtTokenUtil;

    public terradotaController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
   // @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
   @PostMapping("/api/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody authenticationRequest authRequest) throws Exception {
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
           );
       }catch (BadCredentialsException e){
           throw new Exception("Incorrect username and password", e);
       }

    final UserDetails userDetails = userDetailsService       //we give the username the user put in the request body and
         .loadUserByUsername(authRequest.getEmail());      //send it to the loadUserByUsername method of MyUserServicesClass
                                                              //to get the User object with Username, password and Authorities
     final String access_token = jwtTokenUtil.generateToken(userDetails);  //pass the userDetail object to the jwtutil class's generateToken
                                                                 //method which calls the creatToken method of the same class to generate
                                                                 //the token
      //  final String refresh_token = jwtTokenUtil.generateRefreshToken(userDetails);
     return ResponseEntity.ok(new authenticationResponse(access_token)); //ResponseEntity represents an HTTP response, including headers, body, and status. While @ResponseBody puts the return value into the body of the response, ResponseEntity also allows us to add headers and status code.

    }
    @PostMapping({"/test"})
    public String test(){
        return "Test Successful";
    }

    @PostMapping(value ="/dbu/v1/auth/sevis"
            //,consumes = {"application/xml"}
            ,produces = {"application/json"}
            //,produces="text/html"
    )

    public @ResponseBody
    ResponseMessage getStudentBalance2(@RequestBody Terradotta terradotta) throws Exception {

        String requestUrl  = BASE_URL;
        Terradotta terradotta1 = new Terradotta();
       // February, 16 2021 16:49:59

        terradotta1.setSfContactID(terradotta.getSfContactID());
        terradotta1.setAccessGranted(terradotta.getAccessGranted());
        terradotta1.setFirstLoggedIn(terradotta.getFirstLoggedIn());
        terradotta1.setI20RequestCreated(terradotta.getI20RequestCreated());
        terradotta1.setIssueI20(terradotta.getIssueI20());
        terradotta1.setVisaIssueDate(terradotta.getVisaIssueDate());

         HttpEntity<Terradotta> httpEntity = new HttpEntity<>(terradotta1,null);
        //logger.info("HttpEntity",terradotta1);
      /*
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr ="";
        try {
            // Converting the Java object into a JSON string
            jsonStr = Obj.writeValueAsString(terradotta1);
            // Displaying Java object into a JSON string
            //System.out.println(jsonStr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        logger.info(jsonStr);
*/
       // HttpEntity<String> httpEntity = new HttpEntity<String>(jsonStr,null);
        //HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(bodyParamMap, httpHeaders);
       //  logger.info(jsonStr);
        //logger.info("header",httpEntity);
        // transactTransactionalImport;
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, httpEntity,String.class);

       // ResponseEntity<ResponseMessage> response= restTemplate.postForObject(requestUrl,httpEntity,ResponseEntity.class);
       //return restTemplate.postForObject(requestUrl,httpEntity,ResponseEntity.class);

       ResponseMessage responseMessage = new ResponseMessage();
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
           // return  "<cashnet>result=0&respmessage=Successfully Posted</cashnet>";
            responseMessage.setCode("200");
            responseMessage.setMessage("Sevis Data Loaded");

            return responseMessage;
        } else {
            System.out.println(response.getStatusCode());
            responseMessage.setCode("500");
            responseMessage.setMessage("Failure to Load Sevis Data");

            return responseMessage;
        }
       // return jsonStr;
    }
}
