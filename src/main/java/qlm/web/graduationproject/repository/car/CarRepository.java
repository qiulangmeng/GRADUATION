package qlm.web.graduationproject.repository.car;

import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.car.Car;

/**
 * @author qlm
 * @version 1.0 16:36 2020.3.27
 */

public interface CarRepository extends JpaRepository<Car,Long> {
}
