package com.example.sys.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.config.ConstantUtil;
import com.example.sys.entity.SysRole;
import com.example.sys.service.ISysRoleService;
import com.example.wwq.entity.WwqSort;
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
 * 权限表 前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-27
 */
@Controller
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    /****
     * 跳转至权限list
     * @return
     */
    @RequestMapping("list")
    public String list(){
       return "sys/roleList";
    }

    /******
     * 权限列表
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String,Object> listData(Integer page, Integer limit, String name){
        Page<SysRole> sysRolePage=new Page<>();
        sysRolePage.setLimit(limit);
        sysRolePage.setCurrent(page);
        EntityWrapper<SysRole> sysRoleEntityWrapper=new EntityWrapper<>();
        if(StringUtils.isNotBlank(name)){
            sysRoleEntityWrapper.like("name",name);
        }
        sysRoleEntityWrapper.like("del_flag","0");
        sysRoleEntityWrapper.orderBy("create_date",false);

        Page<SysRole> rolePage=sysRoleService.selectPage(sysRolePage,sysRoleEntityWrapper);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",rolePage.getTotal());
        result.put("data",rolePage.getRecords());
        return result;
    }

    /****
     * 跳转至新增权限页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("toAdd")
    public String  toAdd(Model model, String id){
        SysRole sysRole=null;
        if(StringUtils.isNotBlank(id)){
            sysRole=sysRoleService.selectById(id);
        }else{
            sysRole=new SysRole();
        }
        model.addAttribute("sysRole",sysRole);
        return "sys/addRole";
    }

    /****
     * 保存权限信息
     * @param sysRole
     * @return
     */
    @RequestMapping("saveRole")
    @ResponseBody
    public Map<String,Object> saveRole(SysRole sysRole){

        Boolean flag=false;
        Map<String,Object> result=new HashMap<>();
        if(sysRole.getId()!=null){
            sysRole.setCreateDate(new Date());
            sysRole.setUpdateDate(new Date());
            flag=sysRoleService.updateById(sysRole);
        }else{
            flag=sysRoleService.insert(sysRole);
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

    /****
     * 删除权限
     * @return
     */
    @ResponseBody
    @RequestMapping("delRole")
    public Map<String,Object> delRole(Integer id){
        SysRole sysRole=new SysRole();
        sysRole.setId(id);
        sysRole.setDelFlag("1");
        Boolean flag=sysRoleService.updateById(sysRole);
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

