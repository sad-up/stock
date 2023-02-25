package com.hh.stock.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hh.stock.common.core.domain.entity.Permission;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : hh
 * @date : 2023/2/15 11:13
 * @description : Treeselect树结构实体类
 */

public class TreeSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }



    public TreeSelect(Permission menu)
    {
        this.id = menu.getId();
        this.label = menu.getTitle();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }
}
