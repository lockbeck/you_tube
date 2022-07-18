package com.company.dto.subscription;

import com.company.enums.Notification;
import com.company.enums.SubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class SubscriptionUpdateDTO {

    @NotBlank(message = "channelId required MAZGI")
    private String channelId;
    @NotBlank(message = "status required MAZGI")
    private SubscriptionStatus status;
}
