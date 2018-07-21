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
package io.francoisbotha.bdgasadmin.domain.dto;

import javax.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigInteger;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class DataSourceDto {

    private String id;

    @NotBlank
    private String teamId;

    @NotBlank
    private String fileName;

    @NotBlank
    private String objectKey;

    private String contentType;

    private Long contentLength;

    private String userId;

    private String createDt;

}