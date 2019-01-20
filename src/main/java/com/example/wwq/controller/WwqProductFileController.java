package com.example.wwq.controller;


import com.example.wwq.service.IWwqProductFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 商品文件表 前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Controller
@RequestMapping("/wwqProductFile")
public class WwqProductFileController {

    @Autowired
    private IWwqProductFileService wwqProductFileService;

    @ResponseBody
    @RequestMapping("delPic")
    public Map<String,Object> delPic(String id){

        Map<String,Object> result=new HashMap<>();
        Boolean flag=wwqProductFileService.deleteById(id);
        if(flag){
            result.put("code",1);
            result.put("msg","删除成功");
        }else{
            result.put("code",0);
            result.put("msg","删除失败");
        }
        return result;
    }
}

