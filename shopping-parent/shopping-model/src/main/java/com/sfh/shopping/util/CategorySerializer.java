package com.sfh.shopping.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sfh.shopping.model.Category;

import java.io.IOException;

public class CategorySerializer extends StdSerializer<Category> {
    public CategorySerializer() {
        super(Category.class);
    }

    @Override
    public void serialize(Category category, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeStartObject();//对象的开始，相当于"{"

        //generator.writeFieldName("id");
        //generator.writeNumber(category.getId());
        //一句等效于上两句
        generator.writeNumberField("id", category.getId());
        generator.writeStringField("name", category.getName());
        generator.writeStringField("iconClass", category.getIconClass());

        if (category.getParent() != null) {
            generator.writeNumberField("parentId", category.getParent().getId());
            generator.writeStringField("parentName", category.getParent().getName());
        }

        //是否叶子节点
        generator.writeBooleanField("isLeaf", category.getChildren() == null || category.getChildren().size() == 0);

        if (category.getSeq() != null) {
            generator.writeNumberField("seq", category.getSeq());
        }

        generator.writeStringField("description", category.getDescription());

        generator.writeEndObject();//对象的结束，相当于"}"
    }
}
