package com.example.wwq.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.ProductDO;
import com.example.wwq.DO.UserListDO;
import com.example.wwq.entity.WwqUser;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
public interface IWwqUserService extends IService<WwqUser> {

    Page<UserListDO> getAllUser(Page<UserListDO> page, UserListDO userListDO);

    boolean batchImport(String fileName, MultipartFile file) throws Exception;
}
