package com.iitu.kz.kindergardenadmin.model;

import lombok.Data;

import java.util.List;


@Data
public class Group {
    private String id;
    private List<Children> childrenList;
    private String name;
    private Staff supervisor;
}

