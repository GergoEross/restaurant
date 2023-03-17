package com.restaurant.waiter.rest;


import com.restaurant.waiter.datamodel.Order;
import com.restaurant.waiter.datamodel.Status;
import com.restaurant.waiter.dto.InformDTO;
import com.restaurant.waiter.dto.ModifyDTO;
import com.restaurant.waiter.dto.PayDTO;
import com.restaurant.waiter.dto.SaveDTO;
import com.restaurant.waiter.mapper.Mapper;
import com.restaurant.waiter.store.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderRepository service;

    @Autowired
    private Mapper mapper;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres rendelés"),
            @ApiResponse(responseCode = "500", description = "Rendelés nem lehetséges")
    })
    @Operation(summary = "Rendelés felvétel")
    @PostMapping(path = "/save")
    public void save(@Parameter(description = "Rendelés", required = true) @RequestBody(required = true) SaveDTO pData) throws Exception{
        Order order = mapper.toEntityFromSaveDTO(pData);
        service.save(order);
    }
    //vendég tájékosztatása
    @Operation(summary = "Vendég tájékoztatása")
    @GetMapping(path = "/{tableID}")
    public List<InformDTO> informGuest(@Parameter(description = "Asztal ID") @PathVariable(name = "tableID") long tableID){
        List<Order> orders = service.findByTableId(tableID);

        List<InformDTO> informDTOS = new ArrayList<>();
        orders.forEach(order -> informDTOS.add(mapper.toInformDTO(order)));

        return informDTOS;
    }
    //rendelés módosítása
    @Operation(summary = "Rendelés módosítása")
    @PatchMapping(path = "/{id}")
    public void modify(@Parameter(description = "Rendelés ID") @PathVariable(name = "id") long pID, @Parameter(description = "Rendelés módosítás") @RequestBody ModifyDTO pModifyDTO){
        Order order = service.findById(pID).get();

        order.setMenuID(pModifyDTO.getMenuID());
        order.setDescription(pModifyDTO.getDescription());
        order.setPcs(pModifyDTO.getPcs());

        service.save(order);
    }
    //étel kihozás
    @Operation(summary = "Étel kihozása")
    @PatchMapping(path = "/{id}")
    public void serving(@Parameter(description = "Rendelés ID") @PathVariable(name = "id") long pID){
        Order order = service.findById(pID).get();

        order.setStatus(Status.BROUGHT_OUT);

        service.save(order);
    }
    //fizetés
    @Operation(summary = "Fizetés")
    @PostMapping(path = "/pay")
    public void pay(@Parameter(description = "Fizetes") @RequestBody(required = true) PayDTO pData){
        Order order = service.finbByTableIdAndGroup(pData.getTableID(), pData.getGroup());

        order.setStatus(Status.COMPLETED);

        service.save(order);
    }
}
