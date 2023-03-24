package com.restaurant.waiter.mapper;

import com.restaurant.waiter.datamodel.Ordertable;
import com.restaurant.waiter.datamodel.Status;
import com.restaurant.waiter.dto.InformDTO;
import com.restaurant.waiter.dto.SaveDTO;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Component
public class Mapper {
    /*public SaveDTO toSaveDTO(OrderTable pOrderTable){
        SaveDTO saveDTO = new SaveDTO();
        saveDTO.setTableID(pOrderTable.getTableID());
        saveDTO.setMenuID(pOrderTable.getMenuID());
        saveDTO.setDescription(pOrderTable.getDescription());
        saveDTO.setGroupName(pOrderTable.getGroupName());
        saveDTO.setPcs(pOrderTable.getPcs());
        saveDTO.setPlace(pOrderTable.getPlace());
        saveDTO.setStatus(pOrderTable.getStatus());
        saveDTO.setMenuName(pOrderTable.getMenuName());
        saveDTO.setSum(pOrderTable.getSum());
        saveDTO.setUnitPrice(pOrderTable.getUnitPrice());

        return saveDTO;
    }*/
    public Ordertable toEntityFromSaveDTO(SaveDTO pSaveDTO){
        Ordertable orderTable = new Ordertable(pSaveDTO.getTableID(), pSaveDTO.getGroupName(), pSaveDTO.getMenuID(), pSaveDTO.getMenuName(), pSaveDTO.getDescription(), pSaveDTO.getPcs(), pSaveDTO.getStatus(), pSaveDTO.getPlace(), pSaveDTO.getUnitPrice(), pSaveDTO.getSum());
        orderTable.setStatus(Status.IN_PROGRESS);
        orderTable.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));
        orderTable.setModifiedTimeStamp(new Timestamp(System.currentTimeMillis()));

        return orderTable;
    }
   public InformDTO toInformDTO(Ordertable pOrdertable){
       InformDTO informDTO = new InformDTO(pOrdertable.getID(), pOrdertable.getGroupName(), pOrdertable.getMenuID(), pOrdertable.getDescription(), pOrdertable.getPcs(), pOrdertable.getStatus(), pOrdertable.getUnitPrice(), pOrdertable.getSum());

       return  informDTO;
   }
}
