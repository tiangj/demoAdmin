package com.example.wwq.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.ProductDO;
import com.example.wwq.DO.UserListDO;
import com.example.wwq.service.IWwqUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Controller
@RequestMapping("/wwqUser")
public class WwqUserController {

    @Autowired
    private IWwqUserService wwqUserService;

    @RequestMapping("list")
    public String list(){
        return "user/list";
    }

    /*****
     * 加载列表数据
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String,Object> listData(Integer page, Integer limit,String nickname){
        Page<UserListDO> userListDOPage=new Page<>();
        userListDOPage.setLimit(limit);
        userListDOPage.setCurrent(page);
        UserListDO userListDO=new UserListDO();
        if(StringUtils.isNotBlank(nickname)){
            userListDO.setNickName(nickname);
        }
        Page<UserListDO> pageList=wwqUserService.getAllUser(userListDOPage,userListDO);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",pageList.getTotal());
        result.put("data",pageList.getRecords());
        return result;
    }

    @RequestMapping("importUserPage")
    public String importUserPage(){
        return "user/importUserPage";
    }

    @ResponseBody
    @RequestMapping("importUserExcel")
    public Map<String,Object> importUserExcel(@RequestParam("file") MultipartFile file){
        Map<String,Object> result=new HashMap<>();

        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = wwqUserService.batchImport(fileName, file);
            if(a){
                result.put("code",1);
                result.put("msg","导入成功");
            }else{
                result.put("code",0);
                result.put("msg","导入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",1);
            result.put("msg",e);
        }
        return result;
    }
}

