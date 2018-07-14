package io.francoisbotha.bdgasadmin.api.v1.controller;

import io.francoisbotha.bdgasadmin.domain.dto.HelpTextDto;
import io.francoisbotha.bdgasadmin.domain.model.HelpText;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.services.HelpTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class HelpTextController {

    @Autowired
    HelpTextService helpTextService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/helpText", method = RequestMethod.GET)
    public List getHelpTexts () throws EntityNotFoundException {

        log.info("Get HelpTexts");

        return helpTextService.getAll();

    }

    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/helpText/{id}", method = RequestMethod.GET)
    public List getHelpTexts (@PathVariable("id") String id) throws EntityNotFoundException {

        log.info("Get HelpText");

        return helpTextService.getAll(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/helpText", method = RequestMethod.POST, consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public HelpText AddHelpText(@RequestBody @Valid HelpTextDto helpTextDto )  {
        HelpText helpText = new HelpText();
        helpText.setName(helpTextDto.getName());
        return helpTextService.create(helpText);
    }

    /************
     * UPDATE   *
     ************/
    @RequestMapping(value = "/api/v1/helpText/{id}", method = RequestMethod.PATCH, consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    public HelpText UpdateHelpText(@PathVariable("id") String id, @RequestBody @Valid HelpTextDto helpTextDto )
            throws EntityNotFoundException  {
        return helpTextService.update(id, helpTextDto);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/helpText/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteHelpText(@PathVariable("id") String id) throws EntityNotFoundException  {
        helpTextService.delete(id);
    }

}
