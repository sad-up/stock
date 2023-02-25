package com.hh.stock.system.service;

import com.hh.stock.system.domain.Logininfor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_logininfor(系统访问记录)】的数据库操作Service
* @createDate 2023-02-11 18:35:22
*/
public interface LogininforService extends IService<Logininfor> {

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(Logininfor logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public List<Logininfor> selectLogininforList(Logininfor logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    public int deleteLogininforByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     */
    public void cleanLogininfor();

}
