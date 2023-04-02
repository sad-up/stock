package com.hh.stock.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName stock_external_info
 */
@TableName(value ="stock_external_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockExternalInfo implements Serializable {
    /**
     * 主键（无业务意义）
     */
    @TableId
    private String id;

    /**
     * 外盘ID
     */
    private String externalId;

    /**
     * 当前时间
     */
    private Date curTime;

    /**
     * 指数名称
     */
    private String externalName;

    /**
     * 当前点数
     */
    private BigDecimal curPoint;

    /**
     * 涨跌点
     */
    private BigDecimal curPrice;

    /**
     * 涨跌率
     */
    private BigDecimal updownRate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        StockExternalInfo other = (StockExternalInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getExternalId() == null ? other.getExternalId() == null : this.getExternalId().equals(other.getExternalId()))
            && (this.getCurTime() == null ? other.getCurTime() == null : this.getCurTime().equals(other.getCurTime()))
            && (this.getExternalName() == null ? other.getExternalName() == null : this.getExternalName().equals(other.getExternalName()))
            && (this.getCurPoint() == null ? other.getCurPoint() == null : this.getCurPoint().equals(other.getCurPoint()))
            && (this.getCurPrice() == null ? other.getCurPrice() == null : this.getCurPrice().equals(other.getCurPrice()))
            && (this.getUpdownRate() == null ? other.getUpdownRate() == null : this.getUpdownRate().equals(other.getUpdownRate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getExternalId() == null) ? 0 : getExternalId().hashCode());
        result = prime * result + ((getCurTime() == null) ? 0 : getCurTime().hashCode());
        result = prime * result + ((getExternalName() == null) ? 0 : getExternalName().hashCode());
        result = prime * result + ((getCurPoint() == null) ? 0 : getCurPoint().hashCode());
        result = prime * result + ((getCurPrice() == null) ? 0 : getCurPrice().hashCode());
        result = prime * result + ((getUpdownRate() == null) ? 0 : getUpdownRate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", externalId=").append(externalId);
        sb.append(", curTime=").append(curTime);
        sb.append(", externalName=").append(externalName);
        sb.append(", curPoint=").append(curPoint);
        sb.append(", updownPoint=").append(curPrice);
        sb.append(", updownRate=").append(updownRate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}