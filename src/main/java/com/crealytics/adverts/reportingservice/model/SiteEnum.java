package com.crealytics.adverts.reportingservice.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * @author alican.albayrak
 */
@Getter
public enum SiteEnum {

    DESKTOP_WEB("desktop web"),
    MOBILE_WEB("mobile web"),
    ANDROID("android"),
    IOS("iOS");

    private final String value;

    SiteEnum(String value) {
        this.value = value;
    }

    private static final Map<String, SiteEnum> lookup = new HashMap<>();

    static {
        for (SiteEnum actionEnum : SiteEnum.values()) {
            lookup.put(actionEnum.getValue(), actionEnum);
        }
    }

    public static SiteEnum get(String value) {
        return lookup.get(value);
    }


}
