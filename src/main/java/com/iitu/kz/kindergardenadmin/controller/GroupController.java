package com.iitu.kz.kindergardenadmin.controller;

import com.iitu.kz.kindergardenadmin.model.Group;
import com.iitu.kz.kindergardenadmin.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/groups")
public class GroupController {
    @Autowired
    public GroupService childrenService;

    @GetMapping
    public List<Group> getGroups() throws IOException {
        return childrenService.getGroups();
    }

    @PostMapping
    public void createGroup(@RequestBody Group group) {
        childrenService.createGroup(group);
    }

    @PutMapping
    public void getGroups(@RequestBody Group group) throws IOException {
         childrenService.updateGroup(group);
    }

}