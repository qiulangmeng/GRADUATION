package qlm.web.graduationproject.service.good;

import org.springframework.stereotype.Repository;
import qlm.web.graduationproject.entity.good.Sku;

/**
 * @author qlm
 * @version 1.0 18:00 2020.3.26
 */
@Repository
public interface SkuService {
    /**
     * 通过id查找Sku对象
     * @param id 主键
     * @return Sku对象
     */
    Sku findById(Long id);

}
