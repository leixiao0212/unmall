package com.youlai.mall.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.ums.pojo.UmsUser;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public interface UmsUserMapper extends BaseMapper<UmsUser> {


    @Select("<script>" +
            " SELECT * from ums_user " +
            " <if test ='user.nickname !=null and user.nickname.trim() neq \"\" ' >" +
            "       AND nickname like concat('%',#{user.nickname},'%')" +
            " </if>" +
            " ORDER BY gmt_modified DESC, gmt_create DESC" +
            "</script>")
    @Results({
            @Result(property = "addressList", column = "id", many = @Many(select = "com.youlai.mall.ums.mapper.UmsAddressMapper.listByUserId"))
    })
    List<UmsUser> list(Page<UmsUser> page, UmsUser user);


}
