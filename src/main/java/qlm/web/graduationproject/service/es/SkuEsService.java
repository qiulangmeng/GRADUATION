package qlm.web.graduationproject.service.es;

import org.springframework.stereotype.Repository;
import qlm.web.graduationproject.entity.es.SkuEs;

/**
 * @author qlm
 * @version 1.0 17:39 2020.3.26
 */
@Repository
public interface SkuEsService  {
    /**
     * 通过id查 SkuEs
     * @param id 主键
     * @return SkuEs对象
     */
    SkuEs findById(Long id);
}
