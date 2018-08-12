package io.francoisbotha.bdgasadmin.domain.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SjsJobsDto {

    private String duration;

    private String classPath;

    private String startTime;

    private String context;

    private String status;

    private String jobId;

}
