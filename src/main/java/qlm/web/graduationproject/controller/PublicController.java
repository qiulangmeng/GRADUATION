package qlm.web.graduationproject.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qlm.web.graduationproject.entity.es.SkuEs;
import qlm.web.graduationproject.repository.address.AddressRepository;
import qlm.web.graduationproject.repository.es.SkuEsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qlm
 * @version 1.0 11:27 2020.3.22
 */
@RestController
@RequestMapping(value = "/public")
public class PublicController {
    static Logger logger = LogManager.getLogger(PublicController.class);
    @Autowired
    SkuEsRepository skuEsRepository;
    @GetMapping(value = "/postOne")
    public boolean insert(){
        SkuEs skuEs = new SkuEs(1L, "蔬菜-土豆-美味土豆-土豆多多", 30d);
        skuEsRepository.save(skuEs);
        return true;
    }
    @GetMapping(value = "/postAll")
    public boolean insertAll(){
        ArrayList<SkuEs> list = new ArrayList<>();
        list.add(new SkuEs(1L, "蔬菜-土豆-美味土豆-土豆多多", 3000d));
        list.add(new SkuEs(2L, "水果-广西晋宁红富士-精品红富士-水分多特别甜",9.5));
        list.add(new SkuEs(3L, "蔬菜-优选白菜-白菜-新鲜可口", 2d));
        list.add(new SkuEs(4L, "水果-江苏雪花梨-雪花梨-皮薄爽脆",6d));
        list.add(new SkuEs(5L, "蔬菜-经典螺丝椒-螺丝胶-形状独特香而不辣", 8d));
        list.add(new SkuEs(6L, "水果-广州鹅甘-鹅甘-皮薄，味道特别甜有籽",9d));
        list.add(new SkuEs(7L, "蔬菜-初生空心菜-空心菜-细嫩量少", 10d));
        skuEsRepository.saveAll(list);
        return true;
    }
    @GetMapping(value = "/findByContextAndPrice")
    public List<SkuEs> findByContextAndPrice(@Param("context") String context,
                                             @Param("priceMin") Double priceMin,
                                             @Param("priceMax") Double priceMax){
        return  skuEsRepository.findByContextAndPriceBetween(context, priceMin, priceMax);
    }
}
