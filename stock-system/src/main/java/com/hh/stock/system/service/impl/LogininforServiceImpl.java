package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.system.domain.Logininfor;
import com.hh.stock.system.service.LogininforService;
import com.hh.stock.system.mapper.LogininforMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_logininfor(系统访问记录)】的数据库操作Service实现
* @createDate 2023-02-11 18:35:22
*/
@Service
public class LogininforServiceImpl extends ServiceImpl<LogininforMapper, Logininfor>
    implements LogininforService{

    @Override
    public void insertLogininfor(Logininfor logininfor) {

    }

    @Override
    public List<Logininfor> selectLogininforList(Logininfor logininfor) {
        return null;
    }

    @Override
    public int deleteLogininforByIds(Long[] infoIds) {
        return 0;
    }

    @Override
    public void cleanLogininfor() {

    }
}




