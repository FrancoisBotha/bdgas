package io.francoisbotha.bdgasadmin.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class HelpTextDto {

    @NotBlank
    private String lang;

    @NotBlank
    private String name;

    @NotBlank
    private String txt;

}