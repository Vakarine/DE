package org.orgname.app.database;

public enum GenderEnum
{
    MALE("Мужчина"),
    FEMALE("Женщина");

    private final String locale;

    private GenderEnum(String locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return this.locale;
    }
}
