package com.restaurant.waiter.store;

import com.restaurant.waiter.datamodel.Ordertable;
import com.restaurant.waiter.utils.logging.AspectLogger;
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
    @AspectLogger
    public List<Ordertable> findByTableID(long ptableID);
    @AspectLogger
    public Ordertable findByTableIDAndGroupName(long ptableID, String pgroupName);
}
