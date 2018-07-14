package io.francoisbotha.bdgasadmin.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class OrganisationDto {

    private String id;

    @NotBlank
    private String name;

}