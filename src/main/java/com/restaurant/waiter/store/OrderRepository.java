package com.restaurant.waiter.store;

import com.restaurant.waiter.datamodel.OrderTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Rendelések kezelése
 */

@Repository
public interface OrderRepository  extends CrudRepository<OrderTable, Long> {

    /**
     * Rendelés(ek) asztal alapján
     * @param ptableID
     * @return rendelések listája
     */
    public List<OrderTable> findByTableID(long ptableID);
    public OrderTable findByTableIDAndGroupName(long ptableID, String pgroupName);
}
