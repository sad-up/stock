package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.system.domain.Log;
import com.hh.stock.system.service.LogService;
import com.hh.stock.system.mapper.LogMapper;
import org.springframework.stereotype.Service;

/**
* @author Huanghe
* @description 针对表【sys_log(系统日志)】的数据库操作Service实现
* @createDate 2023-02-11 19:14:48
*/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
    implements LogService{

}




