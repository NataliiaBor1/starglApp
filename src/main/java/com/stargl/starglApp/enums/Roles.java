package com.stargl.starglApp.enums;

public enum Roles {
    PARENT, //("PARENT", "parent"),
    CHILD; //("CHILD", "child");

    private String code;
    private String name;

//    private Roles(String code, String name) {
//        this.code = code;
//        this.name = name;
//    }

    public String getCode() {
        return this.code;
    }
    public String getName() {
        return this.name;
    }

    public static Roles getRoleById(String code) {
        return Roles.valueOf(code);
    }
}
