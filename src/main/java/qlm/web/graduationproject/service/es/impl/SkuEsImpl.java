package qlm.web.graduationproject.service.es.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qlm.web.graduationproject.entity.es.SkuEs;
import qlm.web.graduationproject.repository.es.SkuEsRepository;
import qlm.web.graduationproject.service.es.SkuEsService;

import java.util.Optional;

/**
 * @author qlm
 * @version 1.0 17:41 2020.3.26
 */
@Service
public class SkuEsImpl implements SkuEsService {
    @Autowired
    SkuEsRepository skuEsRepository;
    @Override
    public SkuEs findById(Long id) {
        Optional<SkuEs> opt = skuEsRepository.findById(id);
        return opt.orElseGet(SkuEs::new);
    }
}
