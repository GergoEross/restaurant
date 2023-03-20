package com.restaurant.waiter.store;

import com.restaurant.waiter.datamodel.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Rendelések kezelése
 */

@Repository
public interface OrderRepository  extends CrudRepository<Order, Long> {

    /**
     * Rendelés(ek) asztal alapján
     * @param ptableID
     * @return rendelések listája
     */
    public List<Order> findByTableID(long ptableID);
    public Order findByTableIDAndGroupName(long ptableID, String pgroupName);
}
