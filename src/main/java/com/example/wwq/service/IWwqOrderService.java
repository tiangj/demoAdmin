package com.example.wwq.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.OrderListDO;
import com.example.wwq.entity.WwqOrder;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;


/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
public interface IWwqOrderService extends IService<WwqOrder> {


//    List<Map<String, Object>> addShopCartProductCartPreOrder(String ids,String userId);
//
//    Map<String, Object> addProductToOrder(String id,Integer buyNum,String userId);
//
//    PageInfo<Map<String,Object>> shopProductOrderList(String userId, Integer orderStatus, Integer pageNum, Integer pageSize);
//
//    Map<String,Object> getStatusNumBystatus(String userId);
//
//    List<Map<String,Object>> shopProductOrderDetail(String userId,String orderId,Integer orderStatus);

      Page<OrderListDO> getAllOrder(Page<OrderListDO> page, OrderListDO orderListDO);

      OrderListDO getOrderById(String id);

      Map<String,Object> updateOrderStatus(String orderId) throws Exception;

}
