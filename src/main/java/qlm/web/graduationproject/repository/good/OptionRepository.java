package qlm.web.graduationproject.repository.good;

import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.good.Options;

/**
 * @author qlm
 * @version 1.0 16:37 2020.3.27
 */
public interface OptionRepository extends JpaRepository<Options,Long> {
}
