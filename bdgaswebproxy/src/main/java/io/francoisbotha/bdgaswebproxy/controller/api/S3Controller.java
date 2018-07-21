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
package io.francoisbotha.bdgaswebproxy.controller.api;

import io.francoisbotha.bdgaswebproxy.domain.model.S3SingedUrl;
import io.francoisbotha.bdgaswebproxy.services.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Slf4j
@RestController
public class S3Controller {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private S3Service s3Service;


    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/api/v1/s3/signedurl", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public S3SingedUrl getSignedUrl(@RequestParam String fileName, @RequestParam String contentType) {

        try {

            S3SingedUrl s3SingedUrl = s3Service.getSignedUrl(fileName, contentType);
            return s3SingedUrl;

        } catch (RestClientException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
