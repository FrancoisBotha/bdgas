package org.bdgas.adminservice.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SjsJobResultDto {

    private String jobId;

    private String status;

    @JsonDeserialize(using = StringListDeserializer.class)
    private List<String> result;


}
