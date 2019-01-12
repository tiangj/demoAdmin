package com.example.wwq.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.UserScoreListDO;
import com.example.wwq.entity.WwqUserScore;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
public interface WwqUserScoreMapper extends BaseMapper<WwqUserScore> {

    List<UserScoreListDO> getAllUserScore(Page<UserScoreListDO> page, UserScoreListDO userScoreListDO);

}
