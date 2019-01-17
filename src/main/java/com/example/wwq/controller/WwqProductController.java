package com.example.wwq.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.example.config.ConstantUtil;
import com.example.wwq.DO.ProductAddDO;
import com.example.wwq.DO.ProductDO;
import com.example.wwq.entity.*;
import com.example.wwq.kit.JSONResult;
import com.example.wwq.service.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品主表 前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Controller
@RequestMapping("/wwqProduct")
public class WwqProductController {

    @Autowired
    private IWwqProductService wwqProductService;

    @Autowired
    private IWwqProductDetailService wwqProductDetailService;

    @Autowired
    private IWwqProductFileService wwqProductFileService;

    @Autowired
    private IWwqSortService wwqSortService;

    @Autowired
    private IWwqPostDateService wwqPostDateService;

    @Autowired
    private IWwqPostWayService wwqPostWayService;


    /**
     *根据分类名查找商品列表
     * @param sortId 商品一级分类id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value="/shopProductList",produces= "text/html;charset=UTF-8")
    @ResponseBody
    public String shopProductList(@RequestParam(value="productType",required=true) String sortId,
                                  @RequestParam(value="pageNum",defaultValue="1",required=true) Integer pageNum,
                                  @RequestParam(value="pageSize",defaultValue="10",required=true) Integer pageSize){
        PageInfo<Map<String,Object>> pageShopProduct = wwqProductService.shopProductList(sortId,pageNum, pageSize);
        return JSONResult.init(200, "success", pageShopProduct);
    }


    /**
     * 首页店选精品，战略品牌
     * @param type 查询类型（1:店选精品；2：战略品牌）
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value="/shopRecommended",produces= "text/html;charset=UTF-8")
    @ResponseBody
    public String shopRecommended(@RequestParam(value="type",required=true) Integer type,
                                  @RequestParam(value="pageNum",defaultValue="1",required=true) Integer pageNum,
                                  @RequestParam(value="pageSize",defaultValue="4",required=true) Integer pageSize){
        PageInfo<Map<String, Object>> newListingMap = wwqProductService.shopRecommended(pageNum, pageSize,type);
        return JSONResult.init(200, "success", newListingMap);

    }


    /**
     * 首页：根据商品名字搜索商品
     * @param productName 商品名称关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value="/searchShopProductList",produces= "text/html;charset=UTF-8")
    @ResponseBody
    public String searchShopProductList(@RequestParam(value="key",required=false) String productName,
                                        @RequestParam(value="pageNum",defaultValue="1",required=true) Integer pageNum,
                                        @RequestParam(value="pageSize",defaultValue="10",required=true) Integer pageSize){
        PageInfo<Map<String,Object>> pageShopProduct = wwqProductService.searchShopProductList(productName,pageNum, pageSize);
        return JSONResult.init(200, "success", pageShopProduct);
    }


    /**
     *商品详情
     * @param id 商品ID
     * @return
     */
    @RequestMapping(value="/shopProductListInfo",produces= "text/html;charset=UTF-8")
    @ResponseBody
    public String shopProductListInfo(@RequestParam(value="productId",required=true) String id){
       Map<String, Object> shopProductDetail = wwqProductService.shopProductListInfo(id);
        return JSONResult.init(200, "success", shopProductDetail);
    }


    @RequestMapping("list")
    public String list(){
        return "product/list";
    }

    /*****
     * 加载商品列表
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String,Object> listData(Integer page, Integer limit,String productName){
        Page<ProductDO> productDOPage=new Page<>();
        productDOPage.setLimit(limit);
        productDOPage.setCurrent(page);
        ProductDO productDO=new ProductDO();
        if(StringUtils.isNotBlank(productName)){
            productDO.setProductName(productName);
        }
        Page<ProductDO> pageList=wwqProductService.getAllProduct(productDOPage,productDO);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",pageList.getTotal());
        result.put("data",pageList.getRecords());
        return result;
    }

    @RequestMapping("addProduct")
    public String productAdd(Model model,String id){
        ProductAddDO productAddDO=null;
        if(id==null || "".equals(id)){
            productAddDO=new ProductAddDO();
        }else {
            productAddDO = wwqProductService.getProductById(id);
            EntityWrapper<WwqProductDetail> detailEntityWrapper=new EntityWrapper<>();
            detailEntityWrapper.eq("shop_product_id",id);
            WwqProductDetail wwqProductDetail=wwqProductDetailService.selectOne(detailEntityWrapper);
            productAddDO.setDeliveryAreaId(wwqProductDetail.getDeliveryAreaId());
            productAddDO.setPostDateId(wwqProductDetail.getPostDateId());
            productAddDO.setPostWayId(wwqProductDetail.getPostWayId());
            productAddDO.setDetailId(wwqProductDetail.getId());
            productAddDO.setDetailRemark(wwqProductDetail.getRemark());
        }
        model.addAttribute("productAddDO",productAddDO);
        //获取商品类型
        EntityWrapper<WwqSort> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("deleteFlag",0);
        List<WwqSort> sortList=wwqSortService.selectList(entityWrapper);
        model.addAttribute("sortList",sortList);
        //获取配送方式枚举
        EntityWrapper<WwqPostWay> postWayEntityWrapper=new EntityWrapper<>();
        postWayEntityWrapper.eq("delete_flag",0);
        List<WwqPostWay> postWayList=wwqPostWayService.selectList(postWayEntityWrapper);
        model.addAttribute("postWayList",postWayList);
        //获取配送时间枚举
        EntityWrapper<WwqPostDate> postDateEntityWrapper=new EntityWrapper<>();
        postDateEntityWrapper.eq("delete_flag",0);
        List<WwqPostDate> postDateList=wwqPostDateService.selectList(postDateEntityWrapper);
        model.addAttribute("postDateList",postDateList);

        return "product/add";
    }

    /********
     * 删除商品
     * @param id
     * @return
     */
    @RequestMapping("delProduct")
    @ResponseBody
    public Map<String,Object> delProduct(String id){
        Map<String,Object> result=new HashMap<>();
        WwqProduct wwqProduct=new WwqProduct();
        wwqProduct.setId(id);
        wwqProduct.setDeleteFlag(1);
        Boolean flag=wwqProductService.updateById(wwqProduct);
        if(flag){
            result.put("code",1);
            result.put("msg","删除成功");
        }else{
            result.put("code",0);
            result.put("msg","删除失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("saveProduct")
    public Map<String,Object> saveProduct(ProductAddDO productAddDO, HttpServletRequest request){
        String userId=request.getSession().getAttribute(ConstantUtil.SEESION_USER_ID).toString();
        String userName=request.getSession().getAttribute(ConstantUtil.SESSION_USER_NAME).toString();
        Map<String,Object> result=new HashMap<>();
        try {
            result=wwqProductService.saveProduct(productAddDO,userId,userName);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",0);
            result.put("msg","操作失败");
        }
        return result;
    }

    /**********
     * 跳转至保持图片页面
     * @param id
     * @return
     */
    @RequestMapping("addProductPic")
    public String addProductPic(Model model,String id){
        model.addAttribute("id",id);
        return "product/addPic";
    }

    /****
     * 加载商品图片
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("loadProducFiles")
    public Map<String,Object> loadProducFiles(String id){
        //根据图片id获取图片信息
        EntityWrapper<WwqProductFile> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("product_id",id);
        entityWrapper.eq("deleteFlag",0);
        List<WwqProductFile> productFiles=wwqProductFileService.selectList(entityWrapper);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",productFiles.size());
        result.put("data",productFiles);
        return result;
    }
}

