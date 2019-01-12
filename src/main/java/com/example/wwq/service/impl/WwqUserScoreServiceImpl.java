package com.example.wwq.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.wwq.DO.UserScoreListDO;
import com.example.wwq.entity.WwqUserScore;
import com.example.wwq.mapper.WwqUserScoreMapper;
import com.example.wwq.service.IWwqUserScoreService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator-plus123
 * @since 2018-12-29
 */
@Service
public class WwqUserScoreServiceImpl extends ServiceImpl<WwqUserScoreMapper, WwqUserScore> implements IWwqUserScoreService {

    @Autowired
    private WwqUserScoreMapper wwqUserScoreMapper;

    @Override
    public Page<UserScoreListDO> getAllUserScore(Page<UserScoreListDO> page, UserScoreListDO userScoreListDO) {
        page.setRecords(wwqUserScoreMapper.getAllUserScore(page,userScoreListDO));
        return page;
    }
}
