package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.order.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
