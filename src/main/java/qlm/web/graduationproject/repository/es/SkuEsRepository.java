package qlm.web.graduationproject.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import qlm.web.graduationproject.entity.es.SkuEs;

import java.util.List;

/**
 * @author qlm
 * @version 1.0 16:55 2020.3.26
 */
public interface SkuEsRepository extends ElasticsearchRepository<SkuEs,Long> {
    /**
     * 通过上下文和价格区间查找SkuEs对象
     * @param context 上下文
     * @param priceMin 最低价格
     * @param priceMax 最高价格
     * @return SkuEs对象
     */
    List<SkuEs> findByContextAndPriceBetween(String context,Double priceMin,Double priceMax);

}
