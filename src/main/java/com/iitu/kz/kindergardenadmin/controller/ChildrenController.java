package com.iitu.kz.kindergardenadmin.controller;

import com.iitu.kz.kindergardenadmin.model.Children;
import com.iitu.kz.kindergardenadmin.model.Group;
import com.iitu.kz.kindergardenadmin.service.ChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/admin/children")
public class ChildrenController {

    @Autowired
    ChildrenService childrenService;

    @GetMapping
    public List<Children> getChildrens(){
        return childrenService.getChildren();
    }

    @PostMapping
    public void addChildren(@RequestBody Children children) {
        childrenService.addChildren(children);
    }

    @GetMapping("/group")
    public List<Group> getGroups() {
        return childrenService.getGroups();
    }

    @GetMapping("/group/{id}")
    public List<Children> getChildrenListByGroupId(
            @PathVariable(value = "id") String groupId) throws IOException {
        return childrenService.getListChildren(groupId);
    }
}
