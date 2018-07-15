package io.francoisbotha.bdgaswebproxy.domain.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelpTextDto {

    private String id;

    private String lang;

    @NotBlank
    private String name;

    @NotBlank
    private String txt;

}