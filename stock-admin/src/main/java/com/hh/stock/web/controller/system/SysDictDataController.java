package com.hh.stock.web.controller.system;

import com.hh.stock.common.core.controller.BaseController;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.common.core.domain.entity.DictData;
import com.hh.stock.system.service.DictDataService;
import com.hh.stock.system.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * @author : hh
 * @date : 2023/4/13 22:26
 * @description : 数据字典信息
 */
@RestController
@RequestMapping("/api/dict/data")
public class SysDictDataController extends BaseController {

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private DictTypeService dictTypeService;


    @GetMapping("/list")
    public AjaxResult list(DictData dictData)
    {
        return dictDataService.selectDictDataList(dictData);
    }



//    @PostMapping("/export")
//    public void export(HttpServletResponse response, DictData dictData)
//    {
//        List<DictData> list = dictDataService.selectDictDataList(dictData);
//        ExcelUtil<DictData> util = new ExcelUtil<DictData>(DictData.class);
//        util.exportExcel(response, list, "字典数据");
//    }

    /**
     * 查询字典数据详细
     */

    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable Long dictCode)
    {
        return success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType)
    {
        List<DictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data))
        {
            data = new ArrayList<DictData>();
        }
        return success(data);
    }

    /**
     * 新增字典类型
     */

    @PostMapping
    public AjaxResult add(@Validated @RequestBody DictData dict)
    {
        dict.setCreateBy(getUsername());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */

    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DictData dict)
    {
        dict.setUpdateBy(getUsername());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */

    @DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes)
    {
        dictDataService.deleteDictDataByIds(dictCodes);
        return success();
    }

}
