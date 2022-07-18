package com.company.controller;

import com.company.dto.subscription.SubscriptionCreateDTO;
import com.company.dto.subscription.SubscriptionDTO;
import com.company.dto.subscription.SubscriptionUpdateDTO;
import com.company.service.SubscriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Subscription Controller")
@RestController
@RequestMapping("/subs")
@Slf4j
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscribtionService;

    @ApiOperation(value = "Subscribe", notes = "API to subscribe")
    @PostMapping("/public/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody @Valid SubscriptionCreateDTO dto){
        log.info("Request to subscribe to channel{}",dto);
        String response = subscribtionService.subscribe(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Change Status", notes = "API to change status")
    @PutMapping("/public/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody @Valid SubscriptionUpdateDTO dto){
        log.info("Request to subscribe to channel{}",dto);
        String response = subscribtionService.changeStatus(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Change notification", notes = "API to change notification")
    @PutMapping("/public/changeNotification")
    public ResponseEntity<?> changeNotification(@RequestBody @Valid SubscriptionCreateDTO dto){
        log.info("Request to subscribe to channel{}",dto);
        String response = subscribtionService.changeNotification(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get subscription list", notes = "API to get subscription list for user")
    @PutMapping("/public/getList")
    public ResponseEntity<?> getList(){
        log.info("Request to get subscription list for user");
        List<SubscriptionDTO> response = subscribtionService.getSubsList();
        return ResponseEntity.ok(response);
    }
}
