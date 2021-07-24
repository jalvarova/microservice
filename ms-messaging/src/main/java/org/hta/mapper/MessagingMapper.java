package org.hta.mapper;

import com.sendgrid.Response;
import org.hta.domain.document.MessagingDocument;
import org.hta.dto.MessagingRequest;
import org.hta.thirdparty.model.ResponseSengrid;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

import static org.hta.util.ConvertUtil.jsonToString;

@FunctionalInterface
public interface MessagingMapper {

    void demo();

    BiFunction<MessagingRequest, ResponseSengrid, MessagingDocument> apiToEntity =
            (MessagingRequest request, ResponseSengrid response) ->
                    MessagingDocument
                            .builder()
                            .operationNumber(request.getPayload().getOperation())
                            .emailFrom(response.getEmailFrom())
                            .emailTo(request.getEmail())
                            .payload(jsonToString(request))
                            .payloadResponse(jsonToString(response))
                            .sendMessageDate(LocalDateTime.now())
                            .attempts(0)
                            .state(Boolean.TRUE)
                            .build();

}
