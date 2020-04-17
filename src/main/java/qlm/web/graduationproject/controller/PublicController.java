package qlm.web.graduationproject.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qlm.web.graduationproject.entity.es.SkuEs;
import qlm.web.graduationproject.entity.good.Sku;
import qlm.web.graduationproject.repository.address.AddressRepository;
import qlm.web.graduationproject.repository.es.SkuEsRepository;
import qlm.web.graduationproject.repository.good.SkuReposity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author qlm
 * @version 1.0 11:27 2020.3.22
 */
@Controller
@RequestMapping(value = "/public")
public class PublicController {
    static Logger LOG = LogManager.getLogger(PublicController.class);
    @Autowired
    SkuEsRepository skuEsRepository;
    @Autowired
    private SkuReposity skuReposity;
    @GetMapping("/postAllSku")
    public boolean postAllSku(){
        List<Sku> skus = skuReposity.findAll();
        ArrayList<SkuEs> skuEs = new ArrayList<>();
        for (Sku sku:skus
             ) {
            skuEs.add(new SkuEs(sku));
        }
        skuEsRepository.saveAll(skuEs);
        return true;
    }
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
    public String findByContextAndPrice(Model model,
                                             @Param("context") String context,
                                             @Param("priceMin") @DefaultValue("0") Double priceMin,
                                             @Param("priceMax") @DefaultValue("10000") Double priceMax,
                                             @Param("pageNum") @DefaultValue("0") int pageNum,
                                             @Param("pageSize") @DefaultValue("8") int  pageSize){
        List<SkuEs> byContextAndPriceBetween = skuEsRepository.findByContextAndPriceBetween(context, priceMin, priceMax);
        List<Sku> skus = new ArrayList<>();
        for (SkuEs skuEs:
             byContextAndPriceBetween) {
            Optional<Sku> byId = skuReposity.findById(skuEs.getId());
            if(byId.isPresent()){
                skus.add(byId.get());
            }else {
                LOG.warn("您在配置文件的数据库中的商品表中缺少id为"+skuEs.getId()+"的商品行");
            }

        }
        Page<Sku> result = new PageImpl<>(skus);
        model.addAttribute("skus",result);
        return "search";
    }
}
