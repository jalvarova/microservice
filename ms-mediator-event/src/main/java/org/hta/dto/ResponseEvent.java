package org.hta.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseEvent {

    private String message;

    private String responseData;
}
