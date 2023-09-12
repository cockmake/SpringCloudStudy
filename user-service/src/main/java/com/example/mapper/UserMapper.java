package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
