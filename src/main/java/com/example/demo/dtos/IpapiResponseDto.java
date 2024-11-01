package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class IpapiResponseDto {

    private String ip;
    private String type;
    private String continent_code;
    private String continent_name;
    private String country_code;
    private String country_name;
    private String regionCode;
    private String regionName;
    private String city;
    private String zip;
    private double latitude;
    private double longitude;
    private String msa;
    private String dma;
    private double radius;
    private String ipRoutingType;
    private String connectionType;
    private Location location;
    private TimeZone timeZone;
    private Currency currency;
    private Connection connection;
    private Security security;

    @Getter
    @Setter
    public static class Connection {
        private long asn;
        private String isp;
        private String sld;
        private String tld;
        private String carrier;
        private boolean home;
        private String organizationType;
        private String isicCode;
        private String naicsCode;
    }
    @Getter
    @Setter
    public static class Currency {
        private String code;
        private String name;
        private String plural;
        private String symbol;
        private String symbolNative;
    }

    @Getter
    @Setter
    public static class Location {
        private long geoNameID;
        private String capital;
        private List<LanguageDto> languages;
        private String countryFlag;
        private String countryFlagEmoji;
        private String countryFlagEmojiUnicode;
        private String callingCode;
        private boolean isEu;
    }

    @Getter
    @Setter
    public static class Security {
        private Object isProxy;
        private Object proxyType;
        private boolean isCrawler;
        private Object crawlerName;
        private Object crawlerType;
        private boolean isTor;
        private String threatLevel;
        private Object threatTypes;
        private Object proxyLastDetected;
        private Object proxyLevel;
        private Object vpnService;
        private Object anonymizeStatus;
        private boolean hostingFacility;
    }
    @Getter
    @Setter
    public static class TimeZone {
        private String id;
        private OffsetDateTime currentTime;
        private long gmtOffset;
        private String code;
        private boolean isDaylightSaving;
    }
}
