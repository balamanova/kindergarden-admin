package com.iitu.kz.kindergardenadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iitu.kz.kindergardenadmin.model.Role;
import com.iitu.kz.kindergardenadmin.model.Staff;
import com.iitu.kz.kindergardenadmin.util.MethodNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    RestTemplate eurekaRestTemplate;
    ObjectMapper obj = new ObjectMapper();

    public void addStaff(Staff staff) throws JsonProcessingException {
        eurekaRestTemplate.postForObject(
                "http://kindergarden-staff/rest/staff",
                obj.writeValueAsString(staff),
                Staff.class);
    }

    public List<Role> getRoles() {

        List<Role> groupsList = eurekaRestTemplate.getForObject(
                "http://kindergarden-staff/rest/roles",
                List.class);

        if(groupsList==null) {
            throw new MethodNotAllowedException("Internet Error");
        }

        return groupsList;
    }

    public List<Staff> getStaff() {

        List<Staff> childrenList = eurekaRestTemplate.getForObject(
                "http://kindergarden-staff/rest/roles/ck1ffihx20cd7010352ozry3q",
                List.class);

        if(childrenList==null) {
            throw new MethodNotAllowedException("Login or password invalid");
        }

        return childrenList;
    }
}
