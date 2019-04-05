package com.example.wwq.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.OrderListDO;
import com.example.wwq.entity.WwqOrder;
import com.example.wwq.entity.WwqOrderDetail;
import com.example.wwq.entity.WwqShareCount;
import com.example.wwq.service.IWwqOrderDetailService;
import com.example.wwq.service.IWwqOrderService;
import com.example.wwq.service.IWwqShareCountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Controller
@RequestMapping("/wwqOrder")
public class WwqOrderController {


    @Autowired
    private IWwqOrderService wwqOrderService;

    @Autowired
    private IWwqOrderDetailService wwqOrderDetailService;

    @Autowired
    private IWwqShareCountService shareCountService;
//
//    /**
//     * 从购物车下预定单
//     * @param req
//     * @param ids
//     * @return
//     */
//    @RequestMapping(value="/addShopCartProductCartPreOrder",produces="text/html;charset=UTF-8")
//    @ResponseBody
//    public String addShopCartProductCartPreOrder(HttpServletRequest req, @RequestParam(value="shopCartIds",required=true)String ids){
//        String userId = "1";
//        List<Map<String, Object>> retList = wwqOrderService.addShopCartProductCartPreOrder(ids,userId);
//        if(retList == null || retList.size() < 1){
//            return JSONResult.init(500, "fail",null);
//        }else{
//            return JSONResult.init(200, "success",retList);
//        }
//    }
//
//
//    /**
//     * 从商品详情下预定单
//     * @param req
//     * @param id 商品id
//     * @param buyNum 购买数量
//     * @return
//     */
//    @RequestMapping(value="/addProductToOrder",produces="text/html;charset=UTF-8")
//    @ResponseBody
//    public String addProductToOrder(HttpServletRequest req,@RequestParam(value="productId",required=true)String id,
//                                    @RequestParam(value="buyNum",required=true)Integer buyNum){
//        String userId = "1";
//        Map<String, Object> retMap = wwqOrderService.addProductToOrder(id, buyNum,userId);
//        if(retMap == null || retMap.size()<1){
//            return JSONResult.init(500, "fail",null);
//        }else{
//            return JSONResult.init(200, "success",retMap);
//        }
//    }
//
//    /**
//     * 用户订单列表
//     * @param req
//     * @param orderStatus 订单状态（0：全部）
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @RequestMapping(value="/shopProductOrderList",produces="text/html;charset=UTF-8")
//    @ResponseBody
//    public String shopProductOrderList(HttpServletRequest req,
//                                       @RequestParam(value="type",required=true)Integer orderStatus,
//                                       @RequestParam(value="pageNum",defaultValue="1",required=true) Integer pageNum,
//                                       @RequestParam(value="pageSize",defaultValue="10",required=true) Integer pageSize){
//        String userId = "1";
////        String userId = authorHelper.getUserId(req);
////        if(userId == null){
////            return JSONResult.init(301, "success", "user not login");
////        }
//        PageInfo<Map<String, Object>> page = wwqOrderService.shopProductOrderList(userId,orderStatus, pageNum, pageSize);
//        return JSONResult.init(200, "success",page);
//    }
//
//
//    /**
//     * 根据订单状态获得状态数量（已改）
//     * @return
//     */
//    @RequestMapping(value="getStatusNumBystatus",produces="text/html;charset=UTF-8")
//    @ResponseBody
//    public String getStatusNumBystatus(HttpServletRequest req){
//        String userId = "1";
////        String userId = authorHelper.getUserId(req);
////        if(userId == null){
////            return JSONResult.init(301, "success", "user not login");
////        }
//        Map<String, Object> Nummap= wwqOrderService.getStatusNumBystatus(userId);
//        return JSONResult.init(200, "success",Nummap);
//    }
//
//
//    /**
//     * 订单详情
//     * @param req
//     * @param orderId 订单id
//     * @param orderStatus
//     * @return
//     */
//    @RequestMapping(value="/shopProductOrderDetail",produces="text/html;charset=UTF-8")
//    @ResponseBody
//    public String shopProductOrderDetail(HttpServletRequest req,@RequestParam(value="orderId",required=true)String orderId,
//                                         @RequestParam(value="orderStatus",required=true)Integer orderStatus){
//		String userId = "1";
////        String userId = authorHelper.getUserId(req);
////        if(userId == null){
////            return JSONResult.init(301, "success", "user not login");
////        }
//        List<Map<String,Object>> shopProduct = wwqOrderService.shopProductOrderDetail(userId,orderId,orderStatus);
//        return JSONResult.init(200, "success",shopProduct);
//    }

    @RequestMapping("list")
    public String list(){
        return "order/list";
    }

    @ResponseBody
    @RequestMapping("listData")
    public Map<String,Object> listData(Integer page, Integer limit,String productName,String userName){
        Page<OrderListDO> orderListDOPage=new Page<>();
        orderListDOPage.setLimit(limit);
        orderListDOPage.setCurrent(page);
        OrderListDO orderListDO=new OrderListDO();
        if(StringUtils.isNotBlank(productName)){
            orderListDO.setProductName(productName);
        }
        if(StringUtils.isNotBlank(userName)){
            orderListDO.setNickName(userName);
        }
        Page<OrderListDO> pageList=wwqOrderService.getAllOrder(orderListDOPage,orderListDO);
        Map<String,Object> result=new HashMap<>();
        result.put("code",0);
        result.put("msg","");
        result.put("count",pageList.getTotal());
        result.put("data",pageList.getRecords());
        return result;
    }

    @RequestMapping("showOrderDetail")
    public String showOrderDetail(Model model, String orderId){
        OrderListDO orderListDO = wwqOrderService.getOrderById(orderId);
        model.addAttribute("orderInfo",orderListDO);
        return "order/view";
    }

    @ResponseBody
    @RequestMapping("sendProduct")
    public Map<String,Object> sendProduct(String orderId){
        Map<String,Object> result=new HashMap<>();
        try {
            result=wwqOrderService.updateOrderStatus(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",0);
            result.put("msg","操作失败");
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("levelUp")
    public Map<String,Object> levelUp(String userId){
        Map<String,Object> result=new HashMap<>();
        Boolean resultFlag=false;
        try {
            EntityWrapper<WwqShareCount> entityWrapper=new EntityWrapper<>();
            entityWrapper.eq("user_id",userId);
            entityWrapper.eq("delete_flag",0);
            WwqShareCount wwqShareCount=shareCountService.selectOne(entityWrapper);
            if(wwqShareCount!=null){
                wwqShareCount.setUserLevel(2);
                resultFlag=shareCountService.updateById(wwqShareCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",0);
            result.put("msg","操作失败");
        }
        if (resultFlag){
            result.put("code",1);
            result.put("msg","操作成功");
        }else{
            result.put("code",0);
            result.put("msg","操作失败");
        }

        return result;
    }
}

