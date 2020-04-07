package qlm.web.graduationproject.service.good.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qlm.web.graduationproject.entity.good.Sku;
import qlm.web.graduationproject.repository.good.SkuReposity;
import qlm.web.graduationproject.service.good.SkuService;

import java.util.Optional;

/**
 * @author qlm
 * @version 1.0 18:02 2020.3.26
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuReposity skuReposity;

    @Override
    public Sku findById(Long id) {
        Optional<Sku> opt = skuReposity.findById(id);
        return opt.orElseGet(Sku::new);
    }
}
