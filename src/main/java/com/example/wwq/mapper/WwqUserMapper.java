package com.example.wwq.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.ProductDO;
import com.example.wwq.DO.UserListDO;
import com.example.wwq.entity.WwqUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
public interface WwqUserMapper extends BaseMapper<WwqUser> {

    List<UserListDO> getAllUser(Page<UserListDO> page, UserListDO userListDO);

}
