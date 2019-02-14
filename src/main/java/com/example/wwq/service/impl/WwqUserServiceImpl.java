package com.example.wwq.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.util.StringUtil;
import com.example.wwq.DO.UserListDO;
import com.example.wwq.entity.WwqShareUserConcart;
import com.example.wwq.entity.WwqUser;
import com.example.wwq.mapper.WwqShareUserConcartMapper;
import com.example.wwq.mapper.WwqUserMapper;
import com.example.wwq.service.IWwqUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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

    @Autowired
    private WwqShareUserConcartMapper wwqShareUserConcartMapper;

    @Override
    public Page<UserListDO> getAllUser(Page<UserListDO> page, UserListDO userListDO) {
        page.setRecords(wwqUserMapper.getAllUser(page,userListDO));
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
          boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
             isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
         Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        for (int r = 2; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            Cell cell = row.getCell(2);
            cell.setCellType(1);

            //用户手机号
            String phone=cell.getStringCellValue();
            //推荐人的信息(名称与手机号)
            Cell cellThree = row.getCell(3);
            cellThree.setCellType(1);
            String recommendInfo=cellThree.getStringCellValue();

            //推荐人手机号
            String recommendPhone="";
            if(StringUtils.isNotBlank(recommendInfo)){
                recommendPhone=StringUtil.subNumber(recommendInfo);
            }

            //用户等级(298,365)
            Cell cellFiver = row.getCell(5);
            cellFiver.setCellType(1);
            String userLevel=cellFiver.getStringCellValue();

            if(StringUtils.isNotBlank(phone)){

                //根据用户手机号获取用户id
                WwqUser wwqUser=new WwqUser();
                wwqUser.setPhone(phone);
                wwqUser=wwqUserMapper.selectOne(wwqUser);


                //根据推荐人手机号获取推荐人用户id
                WwqUser recommendWwqUser=new WwqUser();
                if(StringUtils.isNotBlank(recommendPhone)){
                    recommendWwqUser.setPhone(recommendPhone);
                    recommendWwqUser=wwqUserMapper.selectOne(recommendWwqUser);
                }


                //修改分销用户关联表中用户的parentId为推荐人id
                 if(wwqUser!=null && recommendWwqUser!=null){

                    WwqShareUserConcart wwqShareUserConcart=new WwqShareUserConcart();
                    wwqShareUserConcart.setUserId(wwqUser.getId());
                    wwqShareUserConcart=wwqShareUserConcartMapper.selectOne(wwqShareUserConcart);
                    wwqShareUserConcart.setParentId(recommendWwqUser.getId());
                    wwqShareUserConcartMapper.updateById(wwqShareUserConcart);

                }

                //修改用户表中用户的等级
                wwqUser.setLevelUp(userLevel);
                wwqUserMapper.updateById(wwqUser);
            }

        }
        return notNull;
    }

}
