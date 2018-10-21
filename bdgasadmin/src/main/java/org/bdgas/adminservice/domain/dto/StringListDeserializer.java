package org.bdgas.adminservice.domain.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StringListDeserializer extends JsonDeserializer<List<String>> {

    @Override
    public List<String> deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        List<String> ret = new ArrayList<>();

        ObjectCodec codec = parser.getCodec();
        TreeNode node = codec.readTree(parser);

        log.info("BUSY DESERIALISING....");
        log.info(node.toString());

        if (node.isArray()){
            log.info("NODE IS ARRAY....");
            for (JsonNode n : (ArrayNode)node){
                ret.add(n.asText());
            }
        } else if (node.isValueNode()){
            log.info("NODE IS TEXT....");
            ret.add( ((JsonNode)node).asText() );
        } else if (node.isObject()) {
            log.info("NODE IS OBJECT....");
            ret.add( node.toString() );
        } else if (node.isContainerNode()) {
            log.info("NODE IS container....");
            ret.add( ((JsonNode)node).asText() );
        }

        log.info("RETURN VALUE");
        log.info(ret.toString());

        return ret;
    }
}