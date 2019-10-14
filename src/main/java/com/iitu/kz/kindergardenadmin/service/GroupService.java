package com.iitu.kz.kindergardenadmin.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iitu.kz.kindergardenadmin.model.Group;
import com.iitu.kz.kindergardenadmin.model.Staff;
import com.iitu.kz.kindergardenadmin.util.QueryBuilder;
import com.iitu.kz.kindergardenadmin.util.UriBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
public class GroupService {


    @Autowired
    private RestTemplate restTemplateEureka;

    private RestTemplate restTemplate = new RestTemplate();
    public ObjectMapper mapper = new ObjectMapper();

    public List<Group> getGroups() throws IOException {
        String createQuery = QueryBuilder.getAllGroups();
        URI uri = UriBuilder.roleUriBuilder(createQuery);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, httpEntityConstructor(createQuery), String.class);
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(result.getBody());
        JsonNode rootNode = (JsonNode) mapper.readTree(parser).get("data").get("allGroups");

        List<Group> groupList = new ArrayList<>();
        for(JsonNode actualNode: rootNode){
            Group group = new Group();
            group.setId(actualNode.get("id").toString().replace("\"", ""));
            group.setName(actualNode.get("name").toString().replace("\"", ""));
            Staff supervisor = new Staff();
            supervisor.setId(actualNode.get("staff").get("id").toString().replace("\"", ""));
            supervisor.setLName(actualNode.get("staff").get("lName").toString().replace("\"", "") + " " + actualNode.get("staff").get("fName").toString().replace("\"", ""));
            group.setSupervisor(supervisor);
            groupList.add(group);
        }
        return groupList;
    }

    private HttpEntity httpEntityConstructor(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.ALL));
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("query", query);
        return new HttpEntity(stringMap, headers);
    }

    public void updateGroup(Group group) {
        String createQuery = QueryBuilder.updateGroup(group);
        URI uri = UriBuilder.roleUriBuilder(createQuery);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, httpEntityConstructor(createQuery), String.class);
    }

    public void createGroup(Group group) {
        String createQuery = QueryBuilder.addGroup(group);
        URI uri = UriBuilder.roleUriBuilder(createQuery);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, httpEntityConstructor(createQuery), String.class);
    }
}
