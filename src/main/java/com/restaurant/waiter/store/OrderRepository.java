package com.restaurant.waiter.store;

import com.restaurant.waiter.datamodel.Ordertable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Rendelések kezelése
 */

@Repository
public interface OrderRepository  extends CrudRepository<Ordertable, Long> {

    /**
     * Rendelés(ek) asztal alapján
     * @param ptableID
     * @return rendelések listája
     */
    public List<Ordertable> findByTableID(long ptableID);
    public Ordertable findByTableIDAndGroupName(long ptableID, String pgroupName);
}
