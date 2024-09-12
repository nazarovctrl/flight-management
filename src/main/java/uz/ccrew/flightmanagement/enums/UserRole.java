package uz.ccrew.flightmanagement.enums;

import java.util.Arrays;

public enum UserRole {
    CUSTOMER, EMPLOYEE, ADMINISTRATOR;

    public static String[] all() {
        return Arrays.stream(UserRole.values()).map(Enum::name).toArray(String[]::new);
    }
}
