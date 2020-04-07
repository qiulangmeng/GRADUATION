package qlm.web.graduationproject.repository.good;

import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.good.Sku;

import java.util.Optional;

/**
 * @author qlm
 * @version 1.0 17:33 2020.3.26
 */
public interface SkuReposity extends JpaRepository<Sku,Long> {
}
