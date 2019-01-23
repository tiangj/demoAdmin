package com.example.wwq.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.WwqShareCountDO;
import com.example.wwq.DO.WwqShareUserListDO;
import com.example.wwq.service.IWwqShareCountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 分销统计表 前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2019-01-23
 */
@Controller
@RequestMapping("/wwqShareCount")
public class WwqShareCountController {

    @Autowired
    private IWwqShareCountService shareCountService;

    @RequestMapping("list")
    public String list(){
        return "share/list";
    }

    @ResponseBody
    @RequestMapping("listData")
    public Map<String,Object> listData(Integer page, Integer limit,String userName){
        Page<WwqShareCountDO> wwqShareCountDOPage=new Page<>();
        wwqShareCountDOPage.setCurrent(page);
        wwqShareCountDOPage.setLimit(limit);
        WwqShareCountDO shareCountDO=new WwqShareCountDO();
        if(StringUtils.isNotBlank(userName)){
            shareCountDO.setUserName(userName);
        }
        Page<WwqShareCountDO> pageList=shareCountService.getAllShareCount(wwqShareCountDOPage,shareCountDO);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",pageList.getTotal());
        result.put("data",pageList.getRecords());
        return result;
    }

    @RequestMapping("toViewShareUser")
    public String toViewShareUser(Model model,String userId){
        model.addAttribute("userId",userId);
        return "share/viewShareUserList";
    }

    @ResponseBody
    @RequestMapping("shareUserListData")
    public Map<String,Object> shareUserListData(Integer page, Integer limit,String userId){
        Page<WwqShareUserListDO> shareUserListDOPage=new Page<>();
        shareUserListDOPage.setCurrent(page);
        shareUserListDOPage.setLimit(limit);
        WwqShareUserListDO shareUserListDO=new WwqShareUserListDO();
        shareUserListDO.setUserId(userId);
        Page<WwqShareUserListDO> pageList=shareCountService.getShareUserList(shareUserListDOPage,shareUserListDO);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",pageList.getTotal());
        result.put("data",pageList.getRecords());
        return result;
    }
}

