package com.iitu.kz.kindergardenadmin.service;

import com.iitu.kz.kindergardenadmin.model.Children;
import com.iitu.kz.kindergardenadmin.model.Group;
import com.iitu.kz.kindergardenadmin.util.MethodNotAllowedException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class ChildrenService {

    @Autowired
    RestTemplate eurekaRestTemplate;

    @HystrixCommand(
            threadPoolKey = "getChildren",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="100"),
                    @HystrixProperty(name="maximumSize", value="120"),
                    @HystrixProperty(name="maxQueueSize", value="50"),
                    @HystrixProperty(name="allowMaximumSizeToDivergeFromCoreSize", value="true"),
            }
    )
    public void addChildren(Children children) {
        eurekaRestTemplate.postForObject(
                "http://kindergarden-children/rest/children",
                children,
                Children.class);
    }

    @HystrixCommand(
            fallbackMethod = "getGroupsFallback",
            threadPoolKey = "getChildren",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="100"),
                    @HystrixProperty(name="maximumSize", value="120"),
                    @HystrixProperty(name="maxQueueSize", value="50"),
                    @HystrixProperty(name="allowMaximumSizeToDivergeFromCoreSize", value="true"),
            }
    )
    public List<Group> getGroups() {

        List<Group> groupsList = eurekaRestTemplate.getForObject(
                "http://kindergarden-children/rest/groups",
                List.class);

        if(groupsList==null) {
            throw new MethodNotAllowedException("Internet Erro");
        }

        return groupsList;
    }

    public List<Group> getGroupsFallback() {
        List<Group> groupList = new ArrayList<>();
        Group group = new Group();
        group.setName("Not available");
        group.setId("-1");
        groupList.add(group);
        return groupList;
    }

    @HystrixCommand(
            fallbackMethod = "getChildrenFallback",
            threadPoolKey = "getChildren",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="100"),
                    @HystrixProperty(name="maximumSize", value="120"),
                    @HystrixProperty(name="maxQueueSize", value="50"),
                    @HystrixProperty(name="allowMaximumSizeToDivergeFromCoreSize", value="true"),
            }
    )
    public List<Children> getChildren() {

        List<Children> childrenList = eurekaRestTemplate.getForObject(
                "http://kindergarden-children/rest/children",
                List.class);

        if(childrenList==null) {
            throw new MethodNotAllowedException("Login or password invalid");
        }

        return childrenList;
    }

    public List<Children> getChildrenFallback() {
        List<Children> childrenList = new ArrayList<>();
        Children children = new Children();
        children.setFName("Not available");
        children.setId("-1");
        children.setLName("Not available");
        childrenList.add(children);
        return childrenList;
    }

    @HystrixCommand(
            fallbackMethod = "getListChildrenFallback",
            threadPoolKey = "getChildren",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="100"),
                    @HystrixProperty(name="maximumSize", value="120"),
                    @HystrixProperty(name="maxQueueSize", value="50"),
                    @HystrixProperty(name="allowMaximumSizeToDivergeFromCoreSize", value="true"),
            }
    )
    public List<Children> getListChildren(String groupId) {
        List<Children> childrenList = eurekaRestTemplate.getForObject(
                "http://kindergarden-children/rest/children/group/" + groupId,
                List.class);

        if(childrenList==null) {
            throw new MethodNotAllowedException("Login or password invalid");
        }

        return childrenList;
    }

    public List<Children> getListChildrenFallback(String groupId) {
        List<Children> childrenList = new ArrayList<>();
        Children children = new Children();
        children.setFName("Not available");
        children.setId("-1");
        children.setLName("Not available");
        childrenList.add(children);
        return childrenList;
    }
}
