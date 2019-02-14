package com.example.wwq.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.UserListDO;
import com.example.wwq.entity.WwqUser;
import com.example.wwq.mapper.WwqProductMapper;
import com.example.wwq.mapper.WwqUserMapper;
import com.example.wwq.service.IWwqUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Service
public class WwqUserServiceImpl extends ServiceImpl<WwqUserMapper, WwqUser> implements IWwqUserService {

    @Autowired
    private WwqUserMapper wwqUserMapper;

    @Override
    public Page<UserListDO> getAllUser(Page<UserListDO> page, UserListDO userListDO) {
        page.setRecords(wwqUserMapper.getAllUser(page,userListDO));
        return page;
    }

    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        return false;
    }
}
