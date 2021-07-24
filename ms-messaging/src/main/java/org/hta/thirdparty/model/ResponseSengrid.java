package org.hta.thirdparty.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseSengrid {

    private String emailFrom;

    private String emailReply;

    private String subject;
}
