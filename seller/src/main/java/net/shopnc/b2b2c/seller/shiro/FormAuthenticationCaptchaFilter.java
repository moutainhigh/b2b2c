package net.shopnc.b2b2c.seller.shiro;

import net.shopnc.b2b2c.constant.UrlSeller;
import net.shopnc.b2b2c.dao.store.StoreDao;
import net.shopnc.b2b2c.dao.store.StoreGradeDao;
import net.shopnc.b2b2c.domain.store.Seller;
import net.shopnc.b2b2c.domain.store.Store;
import net.shopnc.b2b2c.domain.store.StoreGrade;
import net.shopnc.b2b2c.seller.util.SellerSessionHelper;
import net.shopnc.b2b2c.service.store.SellerService;
import net.shopnc.common.entity.ResultEntity;
import net.shopnc.common.util.JsonHelper;
import net.shopnc.common.util.ShopHelper;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dqw on 2015/11/13.
 */
public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {

    protected final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StoreGradeDao storeGradeDao;

    private String captchaParam = "captcha";

    public String getCaptchaParam() {
        return captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        return new UsernamePasswordCaptchaToken(username, password, captcha);
    }

    /**
     * 主要是针对登入成功的处理方法。对于请求头是AJAX的之间返回JSON字符串。
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject,
                                     ServletRequest request,
                                     ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String sellerName = (String)subject.getPrincipal();
        Seller seller = sellerService.findSellerByName(sellerName);

        //更新商家最后登录时间
        seller.setLastLoginTime(ShopHelper.getCurrentTimestamp());
        sellerService.updateSeller(seller);

        //设置商家Session
        SellerSessionHelper.setSellerId(seller.getSellerId());
        SellerSessionHelper.setSellerName(seller.getSellerName());
        SellerSessionHelper.setSellerAvatar(seller.getAvatarUrl());

        if(seller.getStoreId() > 0) {
            SellerSessionHelper.setStoreId(seller.getStoreId());
            Store store = storeDao.get(Store.class, seller.getStoreId());
            StoreGrade storeGrade = storeGradeDao.get(StoreGrade.class, store.getGradeId());
            SellerSessionHelper.setStoreName(store.getStoreName());
            SellerSessionHelper.setIsOwnShop(store.getIsOwnShop());
            SellerSessionHelper.setStoreGoodsLimit(storeGrade.getGoodsLimit());
            SellerSessionHelper.setStoreAlbumLimit(storeGrade.getAlbumLimit());
            SellerSessionHelper.setStoreCommendLimit(storeGrade.getRecommendLimit());
            SellerSessionHelper.setSellerMenu(sellerService.findSellerMenuPermission(seller.getSellerId()));
        } else {
            SellerSessionHelper.setStoreId(0);
            SellerSessionHelper.setStoreAlbumLimit(0);
        }

        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
            //不是ajax请求
            issueSuccessRedirect(request, response);
        } else {
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setCode(ResultEntity.SUCCESS);
            resultEntity.setMessage("登录成功");
            if(seller.getStoreId() > 0) {
                resultEntity.setUrl(UrlSeller.HOME);
            } else {
                resultEntity.setUrl(UrlSeller.OPEN_INFO);
            }

            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            out.println(JsonHelper.toJson(resultEntity));
            out.flush();
            out.close();
        }
        return false;
    }

    /**
     * 主要是处理登入失败的方法
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e,
                                     ServletRequest request,
                                     ServletResponse response) {

        if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
            // 不是ajax请求
            setFailureAttribute(request, e);
            return true;
        }
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String message = e.getClass().getSimpleName();

            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setCode(ResultEntity.FAIL);

            if ("AuthenticationException".equals(message)) {
                resultEntity.setMessage("用户名密码错误");
            } else if ("CaptchaException".equals(message)) {
                resultEntity.setMessage("验证码错误");
            } else if ("NotAllowLoginException".equals(message)) {
                resultEntity.setMessage("账号被禁止登录");
            } else {
                resultEntity.setMessage("登录失败");
            }
            out.println(JsonHelper.toJson(resultEntity));
            out.flush();
            out.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return false;
    }
}
