package com.iitu.kz.kindergardenadmin.service;

import com.iitu.kz.kindergardenadmin.model.Children;
import com.iitu.kz.kindergardenadmin.model.Group;
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

        String apiCredentials = "rest-client:p@ssword";
        String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<Children> entity = new HttpEntity<>(children, headers);

        eurekaRestTemplate.postForObject(
                "http://kindergarden-children/rest/children",
                entity,
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
        String apiCredentials = "rest-client:p@ssword";
        String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<Group> groupsList = eurekaRestTemplate.exchange(
                "http://kindergarden-children/rest/groups",
                HttpMethod.GET,
                entity,
                List.class).getBody();

        if(groupsList==null) {
            throw new MethodNotAllowedException("Internet Error");
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

        String apiCredentials = "rest-client:p@ssword";
        String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<Children> childrenList = eurekaRestTemplate.exchange(
                "http://kindergarden-children/rest/children",
                HttpMethod.GET,
                entity,
                List.class).getBody();

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
        String apiCredentials = "rest-client:p@ssword";
        String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<Children> childrenList = eurekaRestTemplate.exchange(
                "http://kindergarden-children/rest/children/group/" + groupId,
                HttpMethod.GET,
                entity,
                List.class).getBody();

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
