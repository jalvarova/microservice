package org.hta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hta.thirdparty.model.CustomDynamic;
import org.hta.thirdparty.model.DataMail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessagingRequest {


    @NotEmpty(message = "templateId It can not be null")
    @NotNull(message = "templateId It can not be null")
    private String templateId;

    @NotNull(message = "payload It can not be null")
    private DataMail payload;

    @Email(message = "malformed email")
    @NotNull(message = "email It can not be null")
    private String email;

    @NotNull(message = "messageType It can not be null")
    private MESSAGE_TYPE messageType;
}
