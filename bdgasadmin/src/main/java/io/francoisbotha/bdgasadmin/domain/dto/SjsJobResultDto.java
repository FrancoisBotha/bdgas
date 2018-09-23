package io.francoisbotha.bdgasadmin.domain.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SjsJobResultDto {

    private String jobId;

    @JsonDeserialize(using = CustomStringDeserializer.class)
    private List<Map<String, String>> result;

//    @JsonAnySetter
//    private Map<String, String> result;

//    private List<Map<String, String>> result;

//    @JsonAnySetter
//    public void handleUnknownProperty(String key, String value) {
//        System.out.printf("JSON property: %s: %s", key, value);
//    }


}

