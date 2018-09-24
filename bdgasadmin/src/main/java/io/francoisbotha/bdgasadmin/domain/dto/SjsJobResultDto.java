package io.francoisbotha.bdgasadmin.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SjsJobResultDto {

//    {
//        "jobId": "8d5c4aab-5bdf-4cc3-a8fa-6ab9868378a7",
//       "result": [
//                  "{\"col_name\":\"InvNo\",\"data_type\":\"int\"}",
//                  "{\"col_name\":\"ClientName\",\"data_type\":\"string\"}",
//                  "{\"col_name\":\"Amt\",\"data_type\":\"int\"}"
//                 ]
//    }

    private String jobId;

    @JsonDeserialize(using = StringListDeserializer.class)
    private List<String> result;


}
