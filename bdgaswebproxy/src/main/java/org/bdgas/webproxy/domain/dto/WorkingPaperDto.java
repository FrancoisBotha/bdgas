package org.bdgas.webproxy.domain.dto;

import javax.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class WorkingPaperDto {

    private String id;

    @NotBlank
    private String projectId;

    @NotBlank
    private String name;

    private Integer lineCount;

}
