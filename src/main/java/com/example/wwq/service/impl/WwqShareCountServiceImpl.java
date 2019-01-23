package com.example.wwq.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.WwqShareCountDO;
import com.example.wwq.DO.WwqShareUserListDO;
import com.example.wwq.entity.WwqShareCount;
import com.example.wwq.mapper.WwqShareCountMapper;
import com.example.wwq.service.IWwqShareCountService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分销统计表 服务实现类
 * </p>
 *
 * @author generator-plus123
 * @since 2019-01-23
 */
@Service
public class WwqShareCountServiceImpl extends ServiceImpl<WwqShareCountMapper, WwqShareCount> implements IWwqShareCountService {

    @Autowired
    private WwqShareCountMapper wwqShareCountMapper;

    @Override
    public Page<WwqShareCountDO> getAllShareCount(Page<WwqShareCountDO> page, WwqShareCountDO shareCountDO) {
        page.setRecords(wwqShareCountMapper.getAllShareCount(page,shareCountDO));
        return page;
    }

    @Override
    public Page<WwqShareUserListDO> getShareUserList(Page<WwqShareUserListDO> page, WwqShareUserListDO wwqShareUserListDO) {
        page.setRecords(wwqShareCountMapper.getShareUserList(page,wwqShareUserListDO));
        return page;
    }
}
