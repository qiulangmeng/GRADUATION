package qlm.web.graduationproject.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qlm.web.graduationproject.entity.es.SkuEs;
import qlm.web.graduationproject.entity.good.Sku;
import qlm.web.graduationproject.service.es.SkuEsService;
import qlm.web.graduationproject.service.good.SkuService;

import javax.mail.MessagingException;

/**
 * @author qlm
 * @version 1.0 17:26 2020.3.26
 */
@Component
public class SkuChangeUtils {
    @Autowired
    SkuService skuService;
    @Autowired
    SkuEsService skuEsService;

    /**
     * 通过SkuEs的id字段找出对应的Sku
     * @param skuEs skuEs对象
     * @return 对应的Sku对象
     */
    public Sku skuEs2Sku(SkuEs skuEs){
        Sku sku = skuService.findById(skuEs.getId());
        if(sku.getId() == null){
            try {
                SendMailUtil.sendMail("1259301484@qq.com","graduation问题报告",
                        "您在配置文件的数据库中的商品表中缺少id为"+skuEs.getId()+"的商品行");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return sku;
    }

    /**
     * 通过Sku的id字段找出或创建对应的SkuES
     * @param sku sku对象
     * @return skuEs对象
     */
    public SkuEs sku2SkuEs(Sku sku){
        SkuEs skuEs = skuEsService.findById(sku.getId());
        if(skuEs.getId() == null){
            return new SkuEs(sku);
        }
        return skuEs;
    }
}
