package com.sfh.shopping.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sfh.shopping.model.CustomerAddress;

import java.io.IOException;

public class CustomerAddressSerializer extends StdSerializer<CustomerAddress> {
    public CustomerAddressSerializer() {
        super(CustomerAddress.class);
    }

    @Override
    public void serialize(CustomerAddress ua, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", ua.getId());
        gen.writeStringField("name", ua.getName());
        gen.writeStringField("phone", ua.getPhone());
        gen.writeStringField("email", ua.getEmail());
        gen.writeStringField("address", ua.getAddress());

        if (ua.getProvince() != null) {
            gen.writeNumberField("provinceId", ua.getProvince().getId());
            gen.writeStringField("provinceName", ua.getProvince().getName());
        }

        if (ua.getCity() != null) {
            gen.writeNumberField("cityId", ua.getCity().getId());
            gen.writeStringField("cityName", ua.getCity().getName());
        }

        if (ua.getArea() != null) {
            gen.writeNumberField("areaId", ua.getArea().getId());
            gen.writeStringField("areaName", ua.getArea().getName());
        }

        gen.writeEndObject();
    }
}
