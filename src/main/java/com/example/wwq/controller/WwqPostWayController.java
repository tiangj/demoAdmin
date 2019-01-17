package com.example.wwq.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.config.ConstantUtil;
import com.example.wwq.entity.WwqPostWay;
import com.example.wwq.entity.WwqSort;
import com.example.wwq.service.IWwqPostWayService;
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
        Page<WwqPostWay> wwqPostWayPage=new Page<>();
        wwqPostWayPage.setLimit(limit);
        wwqPostWayPage.setCurrent(page);
        EntityWrapper<WwqPostWay> wwqPostWayEntityWrapper=new EntityWrapper<>();
        if(StringUtils.isNotBlank(name)){
            wwqPostWayEntityWrapper.like("post_way_name",name);
        }
        wwqPostWayEntityWrapper.eq("delete_flag","0");
        wwqPostWayEntityWrapper.orderBy("create_date",false);

        Page<WwqPostWay> postWayPage=wwqPostWayService.selectPage(wwqPostWayPage,wwqPostWayEntityWrapper);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",postWayPage.getTotal());
        result.put("data",postWayPage.getRecords());
        return result;
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

    @ResponseBody
    @RequestMapping("savePostWay")
    public Map<String,Object> savePostWay(WwqPostWay wwqPostWay, HttpServletRequest request){
        String userId=request.getSession().getAttribute(ConstantUtil.SEESION_USER_ID).toString();

        Boolean flag=false;
        Map<String,Object> result=new HashMap<>();
        if(StringUtils.isNotBlank(wwqPostWay.getId())){
            wwqPostWay.setCreateDate(new Date());
            wwqPostWay.setUpdateDate(new Date());
            wwqPostWay.setCreateUser(userId);
            wwqPostWay.setUpdateUser(userId);
            flag=wwqPostWayService.updateById(wwqPostWay);
        }else{
            flag=wwqPostWayService.insert(wwqPostWay);
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
    @RequestMapping("delPostWay")
    public Map<String,Object> delPostWay(String id){
        WwqPostWay wwqPostWay=new WwqPostWay();
        wwqPostWay.setId(id);
        wwqPostWay.setDeleteFlag(1);
        Boolean flag=wwqPostWayService.updateById(wwqPostWay);
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

