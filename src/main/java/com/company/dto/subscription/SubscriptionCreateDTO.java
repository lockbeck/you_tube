package com.company.dto.subscription;

import com.company.dto.channel.ChannelDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.enums.Notification;
import com.company.enums.SubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class SubscriptionCreateDTO {

    @NotBlank(message = "channelId required MAZGI")
    private String channelId;
    @NotBlank(message = "notification required MAZGI")
    private Notification notification;
}
