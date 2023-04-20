package com.sfh.shopping.controller;

import com.alibaba.fastjson2.JSON;
import com.sfh.shopping.common.Global;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/rich-editor")
public class EditorController {
    private String configJson;

    @RequestMapping(produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResponseEntity<String> action(String action, MultipartFile file ) throws IOException {
        if (action.equals("config")) {
            if (StringUtils.hasText(configJson)) {//获取相关配置
                return ResponseEntity.ok(configJson);
            }

            InputStream is = EditorController.class.getResourceAsStream("/static/framework/ueditor/config.json");
            configJson = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            return ResponseEntity.ok(configJson);
        }else if (action.equals("upload-image")){
            if (file==null){
                return ResponseEntity.ok(JSON.toJSONString(Map.of("state", "false")));
            }else {
                String oldName = file.getOriginalFilename();
                if (StringUtils.hasText(oldName)){
                    int idx = oldName.lastIndexOf(".");//.下标
                    String ext =oldName.substring(idx); //扩展名
                    File target = new File(Global.UPLOAD_DIRECTORY,"ueditor/image/");
                    if (!target.exists()){
                        boolean b = target.mkdirs();
                        if (!b){
                            return ResponseEntity.ok(JSON.toJSONString(Map.of("state", "false")));
                        }
                    }
                    //新文件名
                    String newName = UUID.randomUUID().toString()+ext;
                    target = new File(target.getCanonicalPath() + "/"+newName);
                    file.transferTo(target);

                    Map<String, Object> map = new HashMap<>();
                    map.put("state","SUCCESS");
                    map.put("url",Global.UPLOAD_SERVER_URL+"/ueditor/image/"+newName);
                    return ResponseEntity.ok(JSON.toJSONString(map));
                }else {
                    return ResponseEntity.ok(JSON.toJSONString(Map.of("state", "false")));
                }
            }
        }
        return null;
    }
}
