package com.sfh.shopping.util;

import java.util.UUID;

public class IdGenerator {
    public static String create(){
        return UUID.randomUUID().toString();
    }
}
