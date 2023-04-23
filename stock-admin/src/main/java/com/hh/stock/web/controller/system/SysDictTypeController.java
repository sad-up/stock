package com.hh.stock.web.controller.system;

import com.hh.stock.common.constant.UserConstants;
import com.hh.stock.common.core.controller.BaseController;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.DictType;
import com.hh.stock.system.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * @author : hh
 * @date : 2023/4/13 22:28
 * @description : 数据字典信息
 */
@RestController
@RequestMapping("/api/dict/type")
public class SysDictTypeController extends BaseController {

    @Autowired
    private DictTypeService dictTypeService;


    @GetMapping("/list")
    public AjaxResult list(DictType dictType)
    {
//        List<DictType> list =
        return dictTypeService.selectDictTypeList(dictType);
//        return AjaxResult.success(list);
    }


//    @PostMapping("/export")
//    public void export(HttpServletResponse response, DictType dictType)
//    {
//        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
//        ExcelUtil<DictType> util = new ExcelUtil<DictType>(DictType.class);
//        util.exportExcel(response, list, "字典类型");
//    }

    /**
     * 查询字典类型详细
     */

    @GetMapping(value = "/{dictId}")
    public AjaxResult getInfo(@PathVariable Long dictId)
    {
        return success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */

    @PostMapping
    public AjaxResult add(@Validated @RequestBody DictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(getUsername());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */

    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(getUsername());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */

    @DeleteMapping("/{dictIds}")
    public AjaxResult remove(@PathVariable Long[] dictIds)
    {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return success();
    }

    /**
     * 刷新字典缓存
     */

    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache()
    {
        dictTypeService.resetDictCache();
        return success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        List<DictType> dictTypes = dictTypeService.selectDictTypeAll();
        return success(dictTypes);
    }
}
