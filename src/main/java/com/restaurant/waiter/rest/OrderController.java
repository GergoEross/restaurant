package com.restaurant.waiter.rest;


import com.restaurant.waiter.datamodel.Ordertable;
import com.restaurant.waiter.datamodel.Status;
import com.restaurant.waiter.dto.InformDTO;
import com.restaurant.waiter.dto.ModifyDTO;
import com.restaurant.waiter.dto.PayDTO;
import com.restaurant.waiter.dto.SaveDTO;
import com.restaurant.waiter.mapper.Mapper;
import com.restaurant.waiter.store.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository service;

    @Autowired
    private Mapper mapper;

    @Operation(
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"order"}),
                    @SecurityRequirement(name = "openid",scopes = {"order"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"order"})
            }
    )
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ordertable> getAll(){
        Iterable<Ordertable> orders = service.findAll();

        return (List<Ordertable>) orders;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres rendelés"),
            @ApiResponse(responseCode = "500", description = "Rendelés nem lehetséges")
    })
    @Operation(summary = "Rendelés felvétel")
    @PostMapping(path = "/save")
    public void save(@Parameter(description = "Rendelés", required = true) @RequestBody(required = true) SaveDTO pData) throws Exception{
        Ordertable orderTable = mapper.toEntityFromSaveDTO(pData);
        service.save(orderTable);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés",
            content = {@Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = InformDTO.class)))}),
            @ApiResponse(responseCode = "500", description = "Nincs ilyen asztal")
    })
    @Operation(summary = "Vendég tájékoztatása")
    @GetMapping(path = "/inform/{tableID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InformDTO> informGuest(@Parameter(description = "Asztal ID") @PathVariable(name = "tableID") long tableID){
        List<Ordertable> ordertables = service.findByTableID(tableID);

        List<InformDTO> informDTOS = new ArrayList<>();
        ordertables.forEach(ordertable -> informDTOS.add(mapper.toInformDTO(ordertable)));

        return informDTOS;
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres módosítás"),
            @ApiResponse(responseCode = "500", description = "Sikertelen módosítás")
    })
    @Operation(summary = "Rendelés módosítása")
    @PatchMapping(path = "/modify/{id}")
    public void modify(@Parameter(description = "Rendelés ID") @PathVariable(name = "id") long pID, @Parameter(description = "Rendelés módosítás") @RequestBody ModifyDTO pModifyDTO){
        Ordertable orderTable = service.findById(pID).get();

        orderTable.setMenuID(pModifyDTO.getMenuID());
        orderTable.setDescription(pModifyDTO.getDescription());
        orderTable.setPcs(pModifyDTO.getPcs());
        orderTable.setModifiedTimeStamp(new Timestamp(System.currentTimeMillis()));

        service.save(orderTable);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres kihozás"),
            @ApiResponse(responseCode = "500", description = "Sikertelen kihozás")
    })
    @Operation(summary = "Étel kihozása")
    @PatchMapping(path = "/serv/{id}")
    public void serving(@Parameter(description = "Rendelés ID") @PathVariable(name = "id") long pID){
        Ordertable orderTable = service.findById(pID).get();

        orderTable.setStatus(Status.BROUGHT_OUT);

        service.save(orderTable);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres fizetés"),
            @ApiResponse(responseCode = "500", description = "Sikertelen fizetés")
    })
    @Operation(summary = "Fizetés")
    @PostMapping(path = "/pay")
    public void pay(@Parameter(description = "Fizetes") @RequestBody(required = true) PayDTO pData){
        Ordertable orderTable = service.findByTableIDAndGroupName(pData.getTableID(), pData.getGroup());

        orderTable.setStatus(Status.COMPLETED);

        service.save(orderTable);
    }
}
