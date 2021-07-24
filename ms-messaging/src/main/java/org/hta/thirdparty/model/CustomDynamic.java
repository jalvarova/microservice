package org.hta.thirdparty.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.Personalization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomDynamic extends Personalization {

    @JsonProperty("dynamic_template_data")
    private DataMail data;

}
