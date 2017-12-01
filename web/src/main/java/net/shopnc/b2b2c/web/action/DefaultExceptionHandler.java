package net.shopnc.b2b2c.web.action;

/**
 * Created by dqw on 2015/01/29.
 */

import net.shopnc.b2b2c.constant.UrlWeb;
import net.shopnc.common.entity.ResultEntity;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class DefaultExceptionHandler {

    protected final Logger logger = Logger.getLogger(getClass());

    /**
     * 全局异常处理
     */
//    @ExceptionHandler({Exception.class})
//    public ModelAndView exception(Exception e) {
//        logger.error(e.toString());
//        return new ModelAndView(UrlWeb.GO_404);
//    }

    /**
     * 全局异常处理
     */
    @ResponseBody
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResultEntity exception(MaxUploadSizeExceededException e) {
    	ResultEntity resultEntity = new ResultEntity();
    	resultEntity.setCode(ResultEntity.FAIL);
        resultEntity.setMessage("图片尺寸过大，请上传小于1M的图片");
        return resultEntity;
    }
}
