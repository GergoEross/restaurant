package com.restaurant.waiter.mapper;

import com.restaurant.waiter.datamodel.Order;
import com.restaurant.waiter.dto.InformDTO;
import com.restaurant.waiter.dto.ModifyDTO;
import com.restaurant.waiter.dto.SaveDTO;
import org.springframework.stereotype.Component;



@Component
public class Mapper {
    public SaveDTO toSaveDTO(Order pOrder){
        SaveDTO saveDTO = new SaveDTO();
        saveDTO.setTableID(pOrder.getTableID());
        saveDTO.setMenuID(pOrder.getMenuID());
        saveDTO.setDescription(pOrder.getDescription());
        saveDTO.setGroup(pOrder.getGroup());
        saveDTO.setPcs(pOrder.getPcs());

        return saveDTO;
    }
    public Order toEntityFromSaveDTO(SaveDTO pSaveDTO){
        Order order = new Order();
        order.setTableID(pSaveDTO.getTableID());
        order.setMenuID(pSaveDTO.getMenuID());
        order.setDescription(pSaveDTO.getDescription());
        order.setGroup(pSaveDTO.getGroup());
        order.setPcs(pSaveDTO.getPcs());

        return order;
    }
   public InformDTO toInformDTO(Order pOrder){
       InformDTO informDTO = new InformDTO(pOrder.getID(), pOrder.getGroup(), pOrder.getMenuID(), pOrder.getDescription(), pOrder.getPcs(), pOrder.getStatus(), pOrder.getUnitPrice(), pOrder.getSum());

       return  informDTO;
   }
}
