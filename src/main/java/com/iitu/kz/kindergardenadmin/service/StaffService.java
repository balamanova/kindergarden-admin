package com.iitu.kz.kindergardenadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iitu.kz.kindergardenadmin.model.Children;
import com.iitu.kz.kindergardenadmin.model.Role;
import com.iitu.kz.kindergardenadmin.model.Staff;
import com.iitu.kz.kindergardenadmin.util.MethodNotAllowedException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {

    @Autowired
    RestTemplate eurekaRestTemplate;
    ObjectMapper obj = new ObjectMapper();

    @HystrixCommand(
            fallbackMethod = "addStaffFallback",
            threadPoolKey = "getStaffData",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="100"),
                    @HystrixProperty(name="maximumSize", value="120"),
                    @HystrixProperty(name="maxQueueSize", value="50"),
                    @HystrixProperty(name="allowMaximumSizeToDivergeFromCoreSize", value="true"),
            })
    public void addStaff(Staff staff) throws JsonProcessingException {

        String apiCredentials = "rest-client:p@ssword";
        String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<String> entity = new HttpEntity<>(obj.writeValueAsString(staff), headers);

        eurekaRestTemplate.exchange(
                "http://kindergarden-staff/rest/staff",
                HttpMethod.POST,
                entity,
                Staff.class);
    }

    @HystrixCommand(
            fallbackMethod = "getRolesFallback",
            threadPoolKey = "getStaffData",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="100"),
                    @HystrixProperty(name="maximumSize", value="120"),
                    @HystrixProperty(name="maxQueueSize", value="50"),
                    @HystrixProperty(name="allowMaximumSizeToDivergeFromCoreSize", value="true"),
            })
    public List<Role> getRoles() {

        String apiCredentials = "rest-client:p@ssword";
        String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<Role> groupsList = eurekaRestTemplate.exchange(
                "http://kindergarden-staff/rest/roles",
                HttpMethod.GET,
                entity,
                List.class).getBody();

        if(groupsList==null) {
            throw new MethodNotAllowedException("Internet Error");
        }

        return groupsList;
    }

    public List<Role> getRolesFallback() {
        return new ArrayList<>();
    }

    @HystrixCommand(
            fallbackMethod = "getStaffFallback",
            threadPoolKey = "getStaffData",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="100"),
                    @HystrixProperty(name="maximumSize", value="120"),
                    @HystrixProperty(name="maxQueueSize", value="50"),
                    @HystrixProperty(name="allowMaximumSizeToDivergeFromCoreSize", value="true"),
            })
    public List<Staff> getStaff() {

        String apiCredentials = "rest-client:p@ssword";
        String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<Staff> childrenList = eurekaRestTemplate.exchange(
                "http://kindergarden-staff/rest/roles/ck1ffihx20cd7010352ozry3q",
                HttpMethod.GET,
                entity,
                List.class).getBody();

        if(childrenList==null) {
            throw new MethodNotAllowedException("Login or password invalid");
        }

        return childrenList;
    }

    public List<Staff> getStaffFallback() {
        return new ArrayList<>();
    }
}
