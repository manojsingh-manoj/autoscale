package com.train;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	private static Boolean isHealthy = true;
	
	@GetMapping("/hello")
	private ResponseEntity<String>  hello(HttpServletRequest request) throws UnknownHostException {
        StringBuilder sb = new StringBuilder();

        String myHostname = InetAddress.getLocalHost().getHostName();
        sb.append("Hello World By: ").append(myHostname).append("<br/>");
        String ipAddress = request.getHeader("X-FORWARDED-FOR");  
        if (ipAddress == null) {  
            ipAddress = request.getRemoteAddr();  
        }
    
        sb.append("Receive Request From: ").append(ipAddress).append("<br/>");
        return new ResponseEntity<String>(sb.toString(),HttpStatus.OK);
    }
	
	
	@GetMapping("/health")
	  public ResponseEntity<String>  health() {
	        if (isHealthy){
	        	return new ResponseEntity<String>("Healthy",HttpStatus.OK);
	        } else {
	        	return new ResponseEntity<String>("Healthy",HttpStatus.BAD_REQUEST);
	        }
	    }
	 
	 @GetMapping("/flip")
	 private ResponseEntity<?> flipHealth() {
	        isHealthy = !isHealthy;
	        return health();
	    }
}
