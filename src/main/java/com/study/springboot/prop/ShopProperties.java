package com.study.springboot.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "study.springboot")
public class ShopProperties {

    private String secretKey;
}
