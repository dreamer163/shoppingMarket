package com.sfh.shopping.api;

import com.sfh.shopping.model.AddressPart;
import com.sfh.shopping.service.CustomerAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/address", produces = "application/json;charset=utf-8")
public class CustomerAddressApi {
    private final CustomerAddressService userAddressService;

    public CustomerAddressApi(CustomerAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    /**
     * 查询某个省的所有市
     */
    @GetMapping("/cities")
    public ResponseEntity<Map<String, Object>> cities(Integer provinceId) {
        List<AddressPart> cities = userAddressService.findAllCities(provinceId);
        if (cities != null) {
            return ResponseEntity.ok(Map.of("cities", cities));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/areas")
    public ResponseEntity<Map<String, Object>> areas(Integer cityId) {
        List<AddressPart> areas = userAddressService.findAllAreas(cityId);
        if (areas != null) {
            return ResponseEntity.ok(Map.of("areas", areas));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
