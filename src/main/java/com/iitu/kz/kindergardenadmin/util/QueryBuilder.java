package com.iitu.kz.kindergardenadmin.util;

import com.iitu.kz.kindergardenadmin.model.Group;

public class QueryBuilder {
    public static String getAllGroups() {
        return "query{\n" +
                "  allGroups{\n" +
                "    id\n" +
                "    name\n" +
                "  staff {\n" +
                "      id\n" +
                "      fName\n" +
                "      lName\n" +
                "    }" +
                "  }\n" +
                "}";
    }

    public static String updateGroup(Group group) {
        return "mutation {\n" +
                "updateGroup(id: \"" + group.getId() + "\",\n" +
                "name: \"" + group.getName() + "\",\n" +
                "staffId: \"" + group.getSupervisor().getId() + "\") {\n" +
                "  id\n" +
                "}\n" +
                "}";
    }

    public static String addGroup(Group group) {
        return "mutation {\n" +
                "  createGroup(\n" +
                "    name: \"" + group.getName() + "\"\n" +
                "  staffId: \"" + group.getSupervisor().getId() + "\"){\n" +
                "    id\n" +
                "  }\n" +
                "}\n";
    }
}
