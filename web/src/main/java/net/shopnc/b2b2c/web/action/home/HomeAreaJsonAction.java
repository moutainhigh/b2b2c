package net.shopnc.b2b2c.web.action.home;

import net.shopnc.b2b2c.dao.AreaDao;
import net.shopnc.b2b2c.domain.Area;
import net.shopnc.b2b2c.exception.ShopException;
import net.shopnc.common.entity.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * 地区
 * Created by shopnc on 2015/11/16.
 */
@Controller
public class HomeAreaJsonAction extends HomeBaseJsonAction {
    @Autowired
    private AreaDao areaDao;

    /**
     * 得到子级地区列表
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "area/list.json/{id}", method = RequestMethod.GET)
    public ResultEntity listChidrenJson(@PathVariable String id) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            HashMap<String, Object> map = new HashMap<String, Object>();
            List<Area> areaList = areaDao.findByAreaParentId(Integer.parseInt(id));
            map.put("areaList", areaList);
            resultEntity.setData(map);
            resultEntity.setCode(ResultEntity.SUCCESS);
            resultEntity.setMessage("操作成功");
        } catch (Exception e) {
            logger.error(e.toString());
            resultEntity.setCode(ResultEntity.FAIL);
            resultEntity.setMessage("操作失败");
        }
        return resultEntity;
    }

    /**
     * 获取地区带一个最大深度限制
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "area/list.json/{maxdeep}/{id}", method = RequestMethod.GET)
    public ResultEntity listChidrenJsonByDeep(@PathVariable int maxdeep,@PathVariable String id) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            HashMap<String, Object> map = new HashMap<String, Object>();
            List<Area> areaList = areaDao.findByAreaParentId(Integer.parseInt(id));
            if (areaList.size() == 0 || areaList.get(0).getAreaDeep() > maxdeep){
                throw new ShopException("无下级");
            }
            map.put("areaList", areaList);
            resultEntity.setData(map);
            resultEntity.setCode(ResultEntity.SUCCESS);
            resultEntity.setMessage("操作成功");
        }catch (ShopException e){
            resultEntity.setCode(ResultEntity.FAIL);
            resultEntity.setMessage(e.getMessage());
        }catch (Exception e) {
            logger.error(e.toString());
            resultEntity.setCode(ResultEntity.FAIL);
            resultEntity.setMessage("操作失败");
        }
        return resultEntity;
    }

}
