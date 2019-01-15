package com.example.wwq.controller;


import com.example.wwq.entity.WwqPostWay;
import com.example.wwq.service.IWwqPostWayService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 * 商品配送方式表 前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Controller
@RequestMapping("/wwqPostWay")
public class WwqPostWayController {

    @Autowired
    private IWwqPostWayService wwqPostWayService;

    @RequestMapping("list")
    public String list(){
      return "postWay/list";
    }

    @ResponseBody
    @RequestMapping("listData")
    public Map<String,Object> listData(Integer page, Integer limit,String name){
        return null;
    }


    @RequestMapping("toAdd")
    public String toAdd(Model model, String id){
        WwqPostWay postWay=null;
        if(StringUtils.isBlank(id)){
            postWay=new WwqPostWay();
        }else{
            postWay=wwqPostWayService.selectById(id);
        }
        model.addAttribute("postWay",postWay);
        return "postWay/add";
    }

}

