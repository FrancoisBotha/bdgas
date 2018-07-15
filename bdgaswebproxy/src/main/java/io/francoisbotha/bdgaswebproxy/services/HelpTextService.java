package io.francoisbotha.bdgaswebproxy.services;

import io.francoisbotha.bdgaswebproxy.domain.dto.HelpTextDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
public class HelpTextService {

    @Autowired
    private EndPointService endPointService;

    @Resource(name = "resttemplatebean")
    RestTemplate restTemplate;

    public List getAll() {

        final String uri = endPointService.getHelpTextEP();

        ResponseEntity<List<HelpTextDto>> response
                = restTemplate.exchange(uri,
                            HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<HelpTextDto>>() {
                     });

        List<HelpTextDto> helpTexts = response.getBody();

        return helpTexts;

    }

    public void getOne() {

        log.debug("In Service: Create");

        String fooResourceUrl
                = "http://localhost:19000/api/v1/helptext/432443214324";


        HelpTextDto helpTextDtoNew  = restTemplate
                .getForObject(fooResourceUrl, HelpTextDto.class);

        log.debug(helpTextDtoNew.getName().toString());
    }

    public void create(HelpTextDto helpTextDto) throws RestClientException {

        final String uri = endPointService.getHelpTextEP();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            helpTextDto.setLang("en");
            log.debug(helpTextDto.toString());

            HttpEntity<HelpTextDto> entity = new HttpEntity<HelpTextDto>(helpTextDto, headers);

            ResponseEntity<HelpTextDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, HelpTextDto.class);

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }

    public void delete(String id) throws RestClientException {

        final String uri = endPointService.getHelpTextEP()
                + "/" + id;

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<String>("", headers);

            ResponseEntity<HelpTextDto> result = restTemplate.exchange(uri, HttpMethod.DELETE, entity, HelpTextDto.class);

        } catch (RestClientException ex) {

            String message = "Failed to post to service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
    }


}
