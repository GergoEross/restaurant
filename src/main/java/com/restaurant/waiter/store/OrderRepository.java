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
     * @param tableID
     * @return rendelések listája
     */
    public List<Order> findByTableId(long tableID);

    @Override
    Optional<Order> findById(Long aLong);
    /**
     * Új rendelés
     * @param pOrder az új rendelés
     * @return rendelés
     */
    @Override
    public Order save(Order pOrder);

    /**
     * Rendelés törlése
     * @param pOrder
     */
    @Override
    public void delete(Order pOrder);
}
