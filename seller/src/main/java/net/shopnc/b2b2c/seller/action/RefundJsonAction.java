package net.shopnc.b2b2c.seller.action;

import net.sf.json.JSONObject;
import net.shopnc.b2b2c.domain.member.Coupons;
import net.shopnc.b2b2c.domain.refund.Refund;
import net.shopnc.b2b2c.exception.ShopException;
import net.shopnc.b2b2c.seller.util.SellerSessionHelper;
import net.shopnc.b2b2c.service.member.MemberService;
import net.shopnc.b2b2c.service.refund.SellerRefundService;
import net.shopnc.common.entity.ResultEntity;
import net.shopnc.common.util.KmsHelper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RefundJsonAction extends BaseJsonAction {

    @Autowired
    private SellerRefundService sellerRefundService;
    @Autowired
    private MemberService memberService;
    /**
     * 退款处理
     *
     * @param sellerState
     * @param sellerMessage
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = "refund/list")
    @RequestMapping(value = "refund/handle", method = RequestMethod.POST)
    public ResultEntity cancel(@RequestParam(value = "refundId", required = true) int refundId,
                               @RequestParam(value = "sellerState", required = true) int sellerState,
                               @RequestParam(value = "sellerMessage", required = true) String sellerMessage) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(ResultEntity.FAIL);
        try {
        	sellerRefundService.saveSellerRefundHandle(refundId, sellerState, sellerMessage, SellerSessionHelper.getStoreId());
            //设置K码失效
        	if(sellerState != 3){//如果卖家同意退货
        		Refund sellInfo = sellerRefundService.getRefundByIds(refundId, SellerSessionHelper.getStoreId());
                HashMap<String, Integer> param = new HashMap<String,Integer>();
                param.put("memberId", sellInfo.getMemberId());
                param.put("ordersId", sellInfo.getOrdersId());
                param.put("storeId", sellInfo.getStoreId());
                param.put("goodsId", sellInfo.getGoodsId());
                List<Coupons> coupon = memberService.getCoupon(param);
            	for(int i = 0 ; i < coupon.size(); i ++){
            		this.deleteCardCoupons(coupon.get(1));
            		// TODO 调用金融平台接口 mode by yfb
            	}
        	}
            resultEntity.setCode(ResultEntity.SUCCESS);
            resultEntity.setMessage("操作成功");
        } catch (ShopException e) {
            resultEntity.setMessage(e.getMessage());
        } catch (Exception e) {
            resultEntity.setMessage("操作失败");
            logger.error(e.getMessage());
        }
        return resultEntity;
    }
    /**
     * 设置卡券失效
     * @param ordersId
     * @return
     */
    private String deleteCardCoupons(Coupons coupon) {
    	String message = "";
    	try {
    		//设置K码失效
	    	HashMap<String, Object> map = new HashMap<String ,Object>();
	        map.put("userId", coupon.getMemberId());
	        map.put("userName", coupon.getMemberId());
	        map.put("cardNo", coupon.getCodeKey());
	        map.put("systemAp", "ec104");
	        map.put("operationTime", new Timestamp(System.currentTimeMillis()));
			JSONObject json = KmsHelper.sendToKms(map, "setKCodeInvalid");
			if(json.get("result").equals(0)){
				try {
					//修改数据库K卡券为失效状态
					coupon.setIsUseful(3);
					memberService.updateCoupons(coupon);
				} catch (Exception e) {
					message = "卡券信息获取失败";
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			message = "设置K码失效时错误";
			e.printStackTrace();
		}
		return message;
	}    
}