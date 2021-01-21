package com.youlai.mall.pms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Data
public class PmsSpuSpecValue extends BaseEntity {

    @TableId(type = IdType.INPUT)
    private Long id;
    private Long spuId;
    private Long specId;
    private String value;
}
