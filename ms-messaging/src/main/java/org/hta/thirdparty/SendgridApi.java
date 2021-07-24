package org.hta.thirdparty;

import com.sendgrid.*;
import lombok.extern.slf4j.Slf4j;
import org.hta.subcriptor.transport.CurrencyTransactionEventDto;
import org.hta.thirdparty.model.CustomDynamic;
import org.hta.thirdparty.model.DataMail;
import org.hta.thirdparty.model.ResponseSengrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SendgridApi {

    @Value("${sendgrid.from}")
    private String emailFrom;

    @Value("${sendgrid.reply}")
    private String emailReply;

    @Value("${sendgrid.subject}")
    private String subject;

    @Value("${sendgrid.key}")
    private String key;

    public ResponseSengrid call(DataMail dataMail, String emailTo, String templateId) throws IOException {

        Email from = new Email(emailFrom);
        Email to = new Email(emailTo);
        Email replyTo = new Email(emailReply);

        CustomDynamic personalization = new CustomDynamic();
        personalization.addTo(to);
        personalization.setSubject(subject);

        personalization.setData(dataMail);

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTemplateId(templateId);
        mail.setReplyTo(replyTo);
        mail.addPersonalization(personalization);

        SendGrid sg = new SendGrid(key);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);

        log.info("Status " + response.getStatusCode());
        log.info("Body " + response.getBody());
        log.info("Headers" + response.getHeaders());

        return ResponseSengrid
                .builder()
                .emailFrom(emailFrom)
                .emailReply(emailReply)
                .subject(subject)
                .build();
    }
}
