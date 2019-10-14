package com.iitu.kz.kindergardenadmin.service;

import com.iitu.kz.kindergardenadmin.model.Children;
import com.iitu.kz.kindergardenadmin.model.Group;
import com.iitu.kz.kindergardenadmin.util.MethodNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class ChildrenService {

    @Autowired
    RestTemplate eurekaRestTemplate;

    public void addChildren(Children children) {
        eurekaRestTemplate.postForObject(
                "http://kindergarden-children/rest/children",
                children,
                Children.class);
    }

    public List<Group> getGroups() {

        List<Group> groupsList = eurekaRestTemplate.getForObject(
                "http://kindergarden-children/rest/groups",
                List.class);

        if(groupsList==null) {
            throw new MethodNotAllowedException("Internet Erro");
        }

        return groupsList;
    }

    public List<Children> getChildren() {

        List<Children> childrenList = eurekaRestTemplate.getForObject(
                "http://kindergarden-children/rest/children",
                List.class);

        if(childrenList==null) {
            throw new MethodNotAllowedException("Login or password invalid");
        }

        return childrenList;
    }

    public List<Children> getListChildren(String groupId) {
        List<Children> childrenList = eurekaRestTemplate.getForObject(
                "http://kindergarden-children/rest/children/group/" + groupId,
                List.class);

        if(childrenList==null) {
            throw new MethodNotAllowedException("Login or password invalid");
        }

        return childrenList;
    }
}
