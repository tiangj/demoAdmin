package com.example.wwq.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.ProductDO;
import com.example.wwq.DO.UserScoreListDO;
import com.example.wwq.service.IWwqUserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Controller
@RequestMapping("/wwqUserScore")
public class WwqUserScoreController {

    @Autowired
    private IWwqUserScoreService wwqUserScoreService;

    @RequestMapping("showScoreInfo")
    public String showScoreInfo(Model model, String userId){
        model.addAttribute("userId",userId);
        return "userScore/list";
    }

    @ResponseBody
    @RequestMapping("listData")
    public Map<String,Object> listData(Integer page, Integer limit,String userId){
        Page<UserScoreListDO> userScoreListDOPage=new Page<>();
        userScoreListDOPage.setLimit(limit);
        userScoreListDOPage.setCurrent(page);
        UserScoreListDO userScoreListDO=new UserScoreListDO();
        userScoreListDO.setUserId(userId);
        Page<UserScoreListDO> pageList=wwqUserScoreService.getAllUserScore(userScoreListDOPage,userScoreListDO);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",pageList.getTotal());
        result.put("data",pageList.getRecords());
        return result;
    }
}

