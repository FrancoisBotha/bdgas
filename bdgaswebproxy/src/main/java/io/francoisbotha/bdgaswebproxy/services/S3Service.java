/*****************************************************************************
 * Copyright 2018 Francois Botha                                             *
 *                                                                           *
 * Licensed under the Apache License, Version 2.0 (the "License");           *
 * you may not use this file except in compliance with the License.          *
 * You may obtain a copy of the License at                                   *
 *                                                                           *
 *  http://www.apache.org/licenses/LICENSE-2.0                               *
 *                                                                           *
 * Unless required by applicable law or agreed to in writing, software       *
 * distributed under the License is distributed on an "AS IS" BASIS,         *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 * See the License for the specific language governing permissions and       *
 * limitations under the License.                                            *
 *                                                                           *
 *****************************************************************************/
package io.francoisbotha.bdgaswebproxy.services;

import io.francoisbotha.bdgaswebproxy.domain.dto.SignRequestDto;
import io.francoisbotha.bdgaswebproxy.domain.model.S3SingedUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;


@Slf4j
@Service
public class S3Service {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    /***************
     * SIGNED URL  *
     ***************/
    public S3SingedUrl getSignedUrl(String fileName, String contentType) {

        final String uri = endPointService.getSignedUrlEP();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            SignRequestDto signRequestDto = new SignRequestDto(fileName, contentType);
            HttpEntity<SignRequestDto> entity = new HttpEntity<SignRequestDto>(signRequestDto, headers);

            log.debug("Returning signed url...1");
            S3SingedUrl s3SingedUrl = restTemplate.postForObject(uri, entity, S3SingedUrl.class);
            log.debug("Returning signed url...2");
            return s3SingedUrl;

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);


            throw ex;
        }

    }

//    private class FormObj {
//        private String fileName;
//        private String contentType;
//
//        FormObj(String fileName, String contentType) {
//            this.fileName = fileName;
//            this.contentType = contentType;
//        }
//
//        public String getFileName() { return this.fileName;}
//        public String getContentType() { return this.contentType;}
//
//    }
}



