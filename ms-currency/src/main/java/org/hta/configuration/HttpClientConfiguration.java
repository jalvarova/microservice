package org.hta.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

//@EnableFeignClients(basePackageClasses = {CustomerFeignClient.class, IdentityFeignClient.class})
@Configuration
public class HttpClientConfiguration {

//    private final ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;
//
//    @Bean
//    public Decoder feignFormDecoder() {
//        return new SpringDecoder(messageConverters);
//    }
//
//    @Bean
//    public Encoder feignFormEncoder() {
//        return new SpringFormEncoder(new SpringEncoder(messageConverters));
//    }

    @Value("${customers.uri}")
    private String uriCustomer;


    @Value("${identity.uri}")
    private String uriIdentity;


    public WebClient customerWebClient() {
        return WebClient
                .builder()
                .baseUrl(uriCustomer)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public WebClient identityWebClient() {
        return WebClient
                .builder()
                .baseUrl(uriIdentity)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
