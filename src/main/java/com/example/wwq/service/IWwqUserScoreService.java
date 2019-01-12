package com.example.wwq.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.UserListDO;
import com.example.wwq.DO.UserScoreListDO;
import com.example.wwq.entity.WwqUserScore;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
public interface IWwqUserScoreService extends IService<WwqUserScore> {

    Page<UserScoreListDO> getAllUserScore(Page<UserScoreListDO> page, UserScoreListDO userScoreListDO);

}
