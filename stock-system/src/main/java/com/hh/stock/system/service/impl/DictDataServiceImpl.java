package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.DictData;
import com.hh.stock.common.core.domain.stockvo.PageResult;
import com.hh.stock.system.service.DictDataService;
import com.hh.stock.system.mapper.DictDataMapper;
import com.hh.stock.common.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_dict_data(字典数据表)】的数据库操作Service实现
* @createDate 2023-04-13 09:13:16
*/
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData>
    implements DictDataService{

    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public AjaxResult selectDictDataList(DictData dictData)
    {
        PageHelper.startPage(1,10);
        List<DictData> pages = dictDataMapper.selectDictDataList(dictData);
        PageInfo<DictData> pageInfo = new PageInfo<>(pages);
        PageResult<DictData> pageResult = new PageResult<>(pageInfo);
        return AjaxResult.success(pageResult);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public DictData selectDictDataById(Long dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    @Override
    public void deleteDictDataByIds(Long[] dictCodes)
    {
        for (Long dictCode : dictCodes)
        {
            DictData data = selectDictDataById(dictCode);
            dictDataMapper.deleteDictDataById(dictCode);
            List<DictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
    }

    /**
     * 新增保存字典数据信息
     *
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(DictData data)
    {
        int row = dictDataMapper.insertDictData(data);
        if (row > 0)
        {
            List<DictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 修改保存字典数据信息
     *
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(DictData data)
    {
        int row = dictDataMapper.updateDictData(data);
        if (row > 0)
        {
            List<DictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }
}




