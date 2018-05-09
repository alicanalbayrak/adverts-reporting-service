package com.crealytics.adverts.reportingservice.domain.enumaration;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author alican.albayrak
 */
public enum SiteEnum {

    desktop_web,
    mobile_web,
    android,
    iOS;

    private static Map<String, SiteEnum> namesMap = new HashMap<>(4);

    static {
        namesMap.put("desktop web", desktop_web);
        namesMap.put("mobile web", mobile_web);
        namesMap.put("android", android);
        namesMap.put("iOS", iOS);
    }

    @JsonCreator
    public static SiteEnum forValue(String value) {
        return namesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, SiteEnum> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }


        return null;
    }

}
