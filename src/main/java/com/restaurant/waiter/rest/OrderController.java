package com.restaurant.waiter.rest;


import com.restaurant.waiter.datamodel.Ordertable;
import com.restaurant.waiter.datamodel.Status;
import com.restaurant.waiter.dto.InformDTO;
import com.restaurant.waiter.dto.ModifyDTO;
import com.restaurant.waiter.dto.PayDTO;
import com.restaurant.waiter.dto.SaveDTO;
import com.restaurant.waiter.mapper.Mapper;
import com.restaurant.waiter.service.BusinessException;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@Validated
public class OrderController {

    @Autowired
    private OrderRepository service;

    @Autowired
    private Mapper mapper;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Ordertable.class)))}),
            @ApiResponse(responseCode = "403", description = "Nincs megfelelő jogosultságod",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Lejárt token",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "302", description = "Nincs bejelentkezve, átirányítás a login oldalra",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Nincsenek rendelések")
    })
    @Operation(
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"waiter"}),
                    @SecurityRequirement(name = "openid",scopes = {"waiter"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"waiter"})
            }
    )
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ordertable> getAll() throws Exception, BusinessException{
        Iterable<Ordertable> orders = service.findAll();
        if(((List<Ordertable>)orders).isEmpty()){
            throw new BusinessException("order.notexist");
        }

        return (List<Ordertable>) orders;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres rendelés"),
            @ApiResponse(responseCode = "403", description = "Nincs megfelelő jogosultságod",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Lejárt token",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "302", description = "Nincs bejelentkezve, átirányítás a login oldalra",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Rendelés nem lehetséges")
    })
    @Operation(
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"waiter"}),
                    @SecurityRequirement(name = "openid",scopes = {"waiter"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"waiter"})
            },
            summary = "Rendelés felvétel")
    @PostMapping(path = "/save")
    public void save(@Valid @Parameter(description = "Rendelés", required = true) @RequestBody(required = true) SaveDTO pData) throws Exception{
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
    public List<InformDTO> informGuest(@Parameter(description = "Asztal ID") @PathVariable(name = "tableID") @Positive long tableID) throws Exception, BusinessException{
        List<Ordertable> ordertables = service.findByTableID(tableID);
        if(ordertables.isEmpty()){
            throw new BusinessException("table.notexist");
        }

        List<InformDTO> informDTOS = new ArrayList<>();
        ordertables.forEach(ordertable -> informDTOS.add(mapper.toInformDTO(ordertable)));

        return informDTOS;
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres módosítás"),
            @ApiResponse(responseCode = "403", description = "Nincs megfelelő jogosultságod",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Lejárt token",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "302", description = "Nincs bejelentkezve, átirányítás a login oldalra",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Sikertelen módosítás")
    })
    @Operation(
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"waiter"}),
                    @SecurityRequirement(name = "openid",scopes = {"waiter"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"waiter"})
            },
            summary = "Rendelés módosítása")
    @PatchMapping(path = "/modify/{id}")
    public void modify(@Parameter(description = "Rendelés ID") @PathVariable(name = "id") @Positive long pID, @Valid @Parameter(description = "Rendelés módosítás") @RequestBody ModifyDTO pModifyDTO) throws IllegalAccessException, InvocationTargetException, BusinessException {
        var raw = service.findById(pID);
        if(!raw.isPresent()){
            throw new BusinessException("order.notexist");
        }
        Ordertable orderTable = raw.get();
        orderTable.setMenuID(pModifyDTO.getMenuID());
        orderTable.setDescription(pModifyDTO.getDescription());
        orderTable.setPcs(pModifyDTO.getPcs());
        orderTable.setModifiedTimeStamp(new Timestamp(System.currentTimeMillis()));

        service.save(orderTable);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres kihozás"),
            @ApiResponse(responseCode = "403", description = "Nincs megfelelő jogosultságod",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Lejárt token",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "302", description = "Nincs bejelentkezve, átirányítás a login oldalra",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Sikertelen kihozás")
    })
    @Operation(
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"waiter"}),
                    @SecurityRequirement(name = "openid",scopes = {"waiter"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"waiter"})
            },
            summary = "Étel kihozása")
    @PatchMapping(path = "/serv/{id}")
    public void serving(@Parameter(description = "Rendelés ID") @PathVariable(name = "id") @Positive long pID) throws BusinessException{
        var orderTable = service.findById(pID);
        if(orderTable.isPresent()){
            Ordertable res = orderTable.get();
            res.setStatus(Status.BROUGHT_OUT);

            service.save(res);
        }
        else{
            throw new BusinessException("order.notexist");
        }
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres fizetés"),
            @ApiResponse(responseCode = "403", description = "Nincs megfelelő jogosultságod",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Lejárt token",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "302", description = "Nincs bejelentkezve, átirányítás a login oldalra",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Sikertelen fizetés")
    })
    @Operation(
            security = {
                    @SecurityRequirement(name = "apikey",scopes = {"waiter"}),
                    @SecurityRequirement(name = "openid",scopes = {"waiter"}),
                    @SecurityRequirement(name = "oauth2",scopes = {"waiter"})
            },
            summary = "Fizetés")
    @PostMapping(path = "/pay")
    public void pay(@Parameter(description = "Fizetes") @RequestBody(required = true) PayDTO pData) throws BusinessException{
        Ordertable orderTable = service.findByTableIDAndGroupName(pData.getTableID(), pData.getGroup());
        if(orderTable != null){
            orderTable.setStatus(Status.COMPLETED);
            service.save(orderTable);
        }
        else{
            throw new BusinessException("order.notexist");
        }
    }
}
