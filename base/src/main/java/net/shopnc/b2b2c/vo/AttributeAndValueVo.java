package net.shopnc.b2b2c.vo;

import net.shopnc.b2b2c.domain.goods.AttributeValue;

import java.util.List;

/**
 * Created by shopnc.feng on 2015-11-23.
 */
public class AttributeAndValueVo {
    /**
     * 属性编号
     */
    private int attributeId;
    /**
     * 属性名称
     */
    private String attributeName;
    /**
     * 分类编号
     */
    private int categoryId;
    /**
     * 属性排序
     */
    private int attributeSort;
    /**
     * 是否显示
     */
    private int isShow;
    /**
     * 属性值
     */
    private String attributeValueNames;
    /**
     * 属性值
     */
    private List<AttributeValue> attributeValueList;

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAttributeSort() {
        return attributeSort;
    }

    public void setAttributeSort(int attributeSort) {
        this.attributeSort = attributeSort;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getAttributeValueNames() {
        return attributeValueNames;
    }

    public void setAttributeValueNames(String attributeValueNames) {
        this.attributeValueNames = attributeValueNames;
    }

    public List<AttributeValue> getAttributeValueList() {
        return attributeValueList;
    }

    public void setAttributeValueList(List<AttributeValue> attributeValueList) {
        this.attributeValueList = attributeValueList;
    }

    @Override
    public String toString() {
        return "AttributeAndValueVo{" +
                "attributeId=" + attributeId +
                ", attributeName='" + attributeName + '\'' +
                ", categoryId=" + categoryId +
                ", attributeSort=" + attributeSort +
                ", isShow=" + isShow +
                ", attributeValueNames='" + attributeValueNames + '\'' +
                ", attributeValueList=" + attributeValueList +
                '}';
    }
}
