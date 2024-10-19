package models.enums;


import java.lang.reflect.Array;
import java.util.Arrays;

public enum ProfileEnum {
    ROLE_ADMIN ("ROLE ADMIN"),
    ROLE_CUSTOMER ("ROLE_CUSTOMER"),
    ROLE_TECHNICIAN ("ROLE TECHNICIAN");


    private final String description;

    ProfileEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ProfileEnum toEnum(final String description) {
        return Arrays.stream(ProfileEnum.values())
                .filter(profileEnum -> profileEnum.description.equals(description))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(description + " is not a valid profile enum"));
    }
}
