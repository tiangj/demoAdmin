package com.example.wwq.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.config.ConstantUtil;
import com.example.sys.entity.SysUser;
import com.example.wwq.entity.WwqBanner;
import com.example.wwq.kit.JSONResult;
import com.example.wwq.service.IWwqBannerService;
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
import java.util.List;
import java.util.Map;

/**
 * <p>
 * banner位表 前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Controller
@RequestMapping("/wwqBanner")
public class WwqBannerController {

@Autowired
private IWwqBannerService wwqBannerService;


    /**
     * 获取首页banner
     * @return
     */
    @RequestMapping("/shopBannerList")
    @ResponseBody
    public String shopBannerList(@RequestParam(value="pageNum",defaultValue="1",required=true) Integer pageNum,
                                 @RequestParam(value="pageSize",defaultValue="10",required=true) Integer pageSize){
        PageInfo<Map<String,Object>> page = wwqBannerService.shopBannerList(pageNum,pageSize);
        return JSONResult.init(200, "success", page);
    }

    @RequestMapping("list")
    public String list(){
        return "banner/list";
    }

    /*****
     * 加载列表数据
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String,Object> listData(Integer page, Integer limit,String remark){
        Page<WwqBanner> wwqBannerPage=new Page<>();
        wwqBannerPage.setLimit(limit);
        wwqBannerPage.setCurrent(page);
        EntityWrapper<WwqBanner> wwqBannerEntityWrapper=new EntityWrapper<>();
        if(StringUtils.isNotBlank(remark)){
            wwqBannerEntityWrapper.like("remark",remark);
        }
        wwqBannerEntityWrapper.like("delete_flag","0");
        wwqBannerEntityWrapper.orderBy("sort");

        Page<WwqBanner> bannerPage=wwqBannerService.selectPage(wwqBannerPage,wwqBannerEntityWrapper);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",bannerPage.getTotal());
        result.put("data",bannerPage.getRecords());
        return result;
    }

    /****
     * 跳转至新增banner位页面
     * @return
     */
    @RequestMapping("addBanner")
    public String addBanner(Model model){
        WwqBanner banner=new WwqBanner();
        model.addAttribute("banner",banner);
        return "banner/add";
    }

    /****
     * 保存banner位
     * @return
     */
    @ResponseBody
    @RequestMapping("saveBanner")
    public Map<String,Object> saveBanner(WwqBanner wwqBanner, HttpServletRequest request){
        String userId=request.getSession().getAttribute(ConstantUtil.SEESION_USER_ID).toString();
        wwqBanner.setCreateDate(new Date());
        wwqBanner.setUpdateDate(new Date());
        wwqBanner.setCreateUser(userId);
        wwqBanner.setUpdateUser(userId);
        boolean flag=wwqBannerService.insert(wwqBanner);
        Map<String,Object> result=new HashMap<>();
        if(flag){
            result.put("code",1);
            result.put("msg","保存成功");
        }else{
            result.put("code",0);
            result.put("msg","保存失败");
        }
        return result;
    }

    /*******
     * 删除banner位
     * @return
     */
    @ResponseBody
    @RequestMapping("delBanner")
    public Map<String,Object> delBanner(String id){
        WwqBanner wwqBanner=new WwqBanner();
        wwqBanner.setId(id);
        wwqBanner.setDeleteFlag(1);
        Boolean flag=wwqBannerService.updateById(wwqBanner);
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

