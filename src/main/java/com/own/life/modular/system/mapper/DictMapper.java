package com.own.life.modular.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.own.life.modular.system.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基础字典 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-03-13
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * where parentIds like ''
     */
    List<Dict> likeParentIds(@Param("dictId") Long dictId);
}
