package com.example.wwq.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.OrderListDO;
import com.example.wwq.DO.WwqShareCountDO;
import com.example.wwq.DO.WwqShareUserListDO;
import com.example.wwq.entity.WwqShareCount;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 分销统计表 服务类
 * </p>
 *
 * @author generator-plus123
 * @since 2019-01-23
 */
public interface IWwqShareCountService extends IService<WwqShareCount> {

    Page<WwqShareCountDO> getAllShareCount(Page<WwqShareCountDO> page, WwqShareCountDO shareCountDO);

    Page<WwqShareUserListDO> getShareUserList(Page<WwqShareUserListDO> page,WwqShareUserListDO wwqShareUserListDO);
}
