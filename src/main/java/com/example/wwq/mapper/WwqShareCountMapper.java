package com.example.wwq.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.WwqShareCountDO;
import com.example.wwq.DO.WwqShareUserListDO;
import com.example.wwq.entity.WwqShareCount;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 分销统计表 Mapper 接口
 * </p>
 *
 * @author generator-plus123
 * @since 2019-01-23
 */
public interface WwqShareCountMapper extends BaseMapper<WwqShareCount> {

    List<WwqShareCountDO> getAllShareCount(Page<WwqShareCountDO> page, WwqShareCountDO shareCountDO);

    List<WwqShareUserListDO> getShareUserList(Page<WwqShareUserListDO> page,WwqShareUserListDO shareUserListDO);
}
