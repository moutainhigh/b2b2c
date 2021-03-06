package net.shopnc.b2b2c.domain.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.shopnc.b2b2c.constant.MessageTemplateCommonUrl;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 店铺站内消息
 * Created by shopnc.feng on 2016-02-04.
 */
@Entity
@Table(name = "store_message")
public class StoreMessage implements Serializable {
    /**
     * 消息编号
     */
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private int messageId;
    /**
     * 消息内容
     */
    @Column(name = "message_content")
    private String messageContent;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @Column(name = "add_time")
    private Timestamp addTime;
    /**
     * 卖家编号
     */
    @Column(name = "seller_id")
    private int sellerId;
    /**
     * 是否已读
     */
    @Column(name = "is_read")
    private int isRead = 0;
    /**
     * 消息模板分类<br/>
     * 会员    交易-1001 退换货-1002 物流-1003 资产-1004<br/>
     * 商家    交易-2001 退换货-2002 商品-2003 运营-2004
     */
    @Column(name = "tpl_class")
    private Integer tplClass;
    /**
     * 特定数据编号
     */
    @Column(name = "sn")
    private String sn;
    /**
     * 消息模板编码
     */
    @Column(name = "tpl_code")
    private String tplCode;
    /**
     * 跳转URL
     */
    @Transient
    private String messageUrl;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public Integer getTplClass() {
        return tplClass;
    }

    public void setTplClass(Integer tplClass) {
        this.tplClass = tplClass;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTplCode() {
        return tplCode;
    }

    public void setTplCode(String tplCode) {
        this.tplCode = tplCode;
    }

    public String getMessageUrl() {
        String string = MessageTemplateCommonUrl.getUrl(tplCode);
        if (string == null || string.isEmpty()) {
            return "javascript:;";
        }
        return string.replace("{$sn}", sn);
    }

    @Override
    public String toString() {
        return "StoreMessage{" +
                "messageId=" + messageId +
                ", messageContent='" + messageContent + '\'' +
                ", addTime=" + addTime +
                ", sellerId=" + sellerId +
                ", isRead=" + isRead +
                ", tplClass=" + tplClass +
                ", sn='" + sn + '\'' +
                ", tplCode='" + tplCode + '\'' +
                ", messageUrl='" + messageUrl + '\'' +
                '}';
    }
}
