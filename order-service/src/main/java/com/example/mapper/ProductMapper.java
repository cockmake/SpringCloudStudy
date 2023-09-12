package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}
