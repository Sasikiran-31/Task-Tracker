package org.example.notificationservice.controller;

import org.example.notificationservice.DTO.Notification_Dto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class notificationController {

    @PostMapping
    public void sendNotification(@RequestBody Notification_Dto notification_Dto){
        System.out.println("User posted notification: [" + notification_Dto.taskDescription() + "]" );
    }
}
