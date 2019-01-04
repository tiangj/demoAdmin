package com.example.wwq.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.config.ConstantUtil;
import com.example.wwq.entity.WwqBanner;
import com.example.wwq.entity.WwqSort;
import com.example.wwq.kit.JSONResult;
import com.example.wwq.service.IWwqSortService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 商品分类表 前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Controller
@RequestMapping("/wwqSort")
public class WwqSortController {

@Autowired
private IWwqSortService wwqSortService;

    /**
     * 商品一级分类
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value="/shopSortList",produces= "text/html;charset=UTF-8")
    @ResponseBody
    public String shopSortFirstList(@RequestParam(value="pageNum",defaultValue="1",required=true) Integer pageNum,
                                    @RequestParam(value="pageSize",defaultValue="10",required=true) Integer pageSize){
        PageInfo<Map<String, Object>> pageShopSort = wwqSortService.shopSortList(pageNum, pageSize);
        return JSONResult.init(200, "success", pageShopSort);
    }

    /**
     * 商品二级分类详情
     * @param id
     * @return
     */
    @RequestMapping(value="/shopSortSecondInfo",produces= "text/html;charset=UTF-8")
    @ResponseBody
    public String shopSortSecondInfo(String id){
        Map<String,Object> shopSort = wwqSortService.shopSortSecondInfo(id);
        return JSONResult.init(200, "success", shopSort);
    }

    /*****
     * 商品类型列表
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "sort/list";
    }

    /******
     * 商品类型列表
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String,Object> listData(Integer page, Integer limit,String name){
        Page<WwqSort> wwqSortPage=new Page<>();
        wwqSortPage.setLimit(limit);
        wwqSortPage.setCurrent(page);
        EntityWrapper<WwqSort> wwqSortEntityWrapper=new EntityWrapper<>();
        if(StringUtils.isNotBlank(name)){
            wwqSortEntityWrapper.like("name",name);
        }
        wwqSortEntityWrapper.like("deleteFlag","0");
        wwqSortEntityWrapper.orderBy("create_date",false);

        Page<WwqSort> sortPage=wwqSortService.selectPage(wwqSortPage,wwqSortEntityWrapper);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",sortPage.getTotal());
        result.put("data",sortPage.getRecords());
        return result;
    }

    /****
     * 跳转至新增商品页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("toAdd")
    public String  toAdd(Model model,String id){
        WwqSort wwqSort=null;
        if(StringUtils.isNotBlank(id)){
            wwqSort=wwqSortService.selectById(id);
        }else{
            wwqSort=new WwqSort();
        }
        model.addAttribute("wwqSort",wwqSort);
        return "sort/add";
    }

    /****
     * 保存商品类型
     * @param wwqSort
     * @return
     */
    @RequestMapping("saveSort")
    @ResponseBody
    public Map<String,Object> saveSort(WwqSort wwqSort, HttpServletRequest request){
        String userId=request.getSession().getAttribute(ConstantUtil.SEESION_USER_ID).toString();

        Boolean flag=false;
        Map<String,Object> result=new HashMap<>();
        if(StringUtils.isNotBlank(wwqSort.getId())){
            wwqSort.setCreateDate(new Date());
            wwqSort.setUpdateDate(new Date());
            wwqSort.setCreateUser(userId);
            wwqSort.setUpdateUser(userId);
            flag=wwqSortService.updateById(wwqSort);
        }else{
            flag=wwqSortService.insert(wwqSort);
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
     * 删除商品类型
     * @return
     */
    @ResponseBody
    @RequestMapping("delSort")
    public Map<String,Object> delSort(String id){
        WwqSort wwqSort=new WwqSort();
        wwqSort.setId(id);
        wwqSort.setDeleteFlag(1);
        Boolean flag=wwqSortService.updateById(wwqSort);
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

