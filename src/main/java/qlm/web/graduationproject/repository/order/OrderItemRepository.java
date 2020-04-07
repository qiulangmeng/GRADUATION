package qlm.web.graduationproject.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.order.OrderItem;

/**
 * @author qlm
 * @version 1.0 16:56 2020.3.27
 */
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
