package com.sfh.shopping.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sfh.shopping.model.Goods;


import java.io.IOException;

public class GoodsSerializer extends StdSerializer<Goods> {
    public GoodsSerializer() {
        super(Goods.class);
    }

    @Override
    public void serialize(Goods good, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", good.getId());
        gen.writeStringField("spuNo", good.getSpuNo());
        gen.writeStringField("name", good.getName());
        if (good.getCategory() != null) {
            gen.writeStringField("categoryName", good.getCategory().getName());
        }
        gen.writeNumberField("markPrice", good.getMarkPrice());
        gen.writeNumberField("price", good.getPrice());
        if (good.getBrand() != null) {
            gen.writeStringField("brandName", good.getBrand().getName());
        }
        gen.writeStringField("picUrl", good.getPicUrl());
        gen.writeBooleanField("bestSeller", good.getBestSeller());
        gen.writeBooleanField("newProduct", good.getNewProduct());
        gen.writeBooleanField("takeOff", good.getTakeOff());
        gen.writeObjectField("createTime", good.getCreateTime());
        gen.writeObjectField("updateTime", good.getUpdateTime());
        gen.writeStringField("description", good.getDescription());
        gen.writeEndObject();
    }
}
