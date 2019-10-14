package com.iitu.kz.kindergardenadmin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iitu.kz.kindergardenadmin.model.Role;
import com.iitu.kz.kindergardenadmin.model.Staff;
import com.iitu.kz.kindergardenadmin.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/staff")
public class StaffController {

    @Autowired
    StaffService staffService;

    @GetMapping
    public List<Staff> getChildrens(){
        return staffService.getStaff();
    }

    @PostMapping
    public void addChildren(@RequestBody Staff staff) throws JsonProcessingException {
        staffService.addStaff(staff);
    }

    @GetMapping("/role")
    public List<Role> getRoles() {
        return staffService.getRoles();
    }
}
