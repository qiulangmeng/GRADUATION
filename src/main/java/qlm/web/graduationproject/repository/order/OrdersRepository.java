package qlm.web.graduationproject.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.order.Orders;

/**
 * @author qlm
 * @version 1.0 16:56 2020.3.27
 */
public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
