package io.francoisbotha.bdgasadmin.domain.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SjsJobResultDto  {

    private String jobId;

    private String[] result;

}

