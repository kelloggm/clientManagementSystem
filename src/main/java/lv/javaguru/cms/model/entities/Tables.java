package lv.javaguru.cms.model.entities;

public enum Tables {

    PARTICIPANT("course_participant"),
    COURSE("course"),
    COMPANY("company"),
    CLIENT("client"),
    SYSTEM_USER_ROLE("system_user_role"),
    SYSTEM_USER("system_user");

    private String dbName;

    Tables(java.lang.String dbName) {
        this.dbName = dbName;
    }

    public java.lang.String getDbName() {
        return dbName;
    }
}
