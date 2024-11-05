package com.example.demo.Controllers;


import com.example.demo.Service.IpapiService;
import com.example.demo.dtos.CountryInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/ips", produces = "application/json; charset=utf-8")
@RequiredArgsConstructor
public class IpController {

    private final IpapiService ipapiService;

    @GetMapping("/{ip}")
    public CountryInfoDto getIpGeolocationData(@PathVariable String ip) {
        if(ip == null || !isValidIp(ip)) {
            throw new IllegalArgumentException("Invalid ip address format");
        }
        return ipapiService.getCountryData(ip);
    }

    private boolean isValidIp(String ip) {
        String ipv4Pattern = "^(?:\\d{1,3}\\.){3}\\d{1,3}$";
        String ipv6Pattern = "^(?:[\\da-fA-F]{1,4}:){7}[\\da-fA-F]{1,4}$";
        return ip.matches(ipv4Pattern) || ip.matches(ipv6Pattern);
    }
}
