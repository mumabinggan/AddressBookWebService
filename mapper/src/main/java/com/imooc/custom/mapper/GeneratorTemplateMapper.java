package com.imooc.custom.mapper;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface GeneratorTemplateMapper<T> extends Mapper<T>, MySqlMapper {
}
