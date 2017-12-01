package net.shopnc.b2b2c.dao.goods;

import net.shopnc.b2b2c.domain.goods.CategoryLabel;
import net.shopnc.common.dao.BaseDaoHibernate4;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by shopnc.feng on 2015-11-02.
 */
@Repository
@Transactional(rollbackFor = {Exception.class})
public class CategoryLabelDao extends BaseDaoHibernate4<CategoryLabel> {
}
