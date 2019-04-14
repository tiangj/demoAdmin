package com.example.wwq.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.config.ConstantUtil;
import com.example.wwq.entity.WwqPostDate;
import com.example.wwq.service.IWwqPostDateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 送货时间表 前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Controller
@RequestMapping("/wwqPostDate")
public class WwqPostDateController {

    @Autowired
    private IWwqPostDateService wwqPostDateService;

    @RequestMapping("list")
    public String list(){
        return "postDate/list";
    }

    @ResponseBody
    @RequestMapping("listData")
    public Map<String,Object> listData(Integer page, Integer limit,String name){
        Page<WwqPostDate> wwqPostDatePage=new Page<>();
        wwqPostDatePage.setLimit(limit);
        wwqPostDatePage.setCurrent(page);
        EntityWrapper<WwqPostDate> wwqPostDateEntityWrapper=new EntityWrapper<>();
        if(StringUtils.isNotBlank(name)){
            wwqPostDateEntityWrapper.like("post_date_name",name);
        }
        wwqPostDateEntityWrapper.eq("delete_flag","0");
        wwqPostDateEntityWrapper.orderBy("create_date",false);

        Page<WwqPostDate> postWayPage=wwqPostDateService.selectPage(wwqPostDatePage,wwqPostDateEntityWrapper);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",postWayPage.getTotal());
        result.put("data",postWayPage.getRecords());
        return result;
    }


    @RequestMapping("toAdd")
    public String toAdd(Model model, String id){
        WwqPostDate postDate=null;
        if(StringUtils.isBlank(id)){
            postDate=new WwqPostDate();
        }else{
            postDate=wwqPostDateService.selectById(id);
        }
        model.addAttribute("postDate",postDate);
        return "postDate/add";
    }

    @ResponseBody
    @RequestMapping("savePostDate")
    public Map<String,Object> savePostDate(WwqPostDate wwqPostDate, HttpServletRequest request){
        String userId=request.getSession().getAttribute(ConstantUtil.SEESION_USER_ID).toString();

        Boolean flag=false;
        Map<String,Object> result=new HashMap<>();
        if(StringUtils.isNotBlank(wwqPostDate.getId())){
            //wwqPostDate.setCreateDate(new Date());
            //wwqPostDate.setUpdateDate(new Date());
            wwqPostDate.setCreateUser(userId);
            wwqPostDate.setUpdateUser(userId);
            flag=wwqPostDateService.updateById(wwqPostDate);
        }else{
            wwqPostDate.setCreateDate(new Date());
            wwqPostDate.setUpdateDate(new Date());
            wwqPostDate.setCreateUser(userId);
            wwqPostDate.setUpdateUser(userId);
            flag=wwqPostDateService.insert(wwqPostDate);
        }
        if(flag){
            result.put("code",1);
            result.put("msg","操作成功");
        }else{
            result.put("code",0);
            result.put("msg","操作失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("delPostDate")
    public Map<String,Object> delPostDate(String id){
        WwqPostDate wwqPostDate=new WwqPostDate();
        wwqPostDate.setId(id);
        wwqPostDate.setDeleteFlag(1);
        Boolean flag=wwqPostDateService.updateById(wwqPostDate);
        Map<String,Object> result=new HashMap<>();
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

