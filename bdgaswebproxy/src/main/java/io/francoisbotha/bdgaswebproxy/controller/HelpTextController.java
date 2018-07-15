package io.francoisbotha.bdgaswebproxy.controller;

import io.francoisbotha.bdgaswebproxy.domain.dto.HelpTextDto;
import io.francoisbotha.bdgaswebproxy.services.HelpTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class HelpTextController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private HelpTextService helpTextService;

    private static final String BASE_PATH = "/pages/admin/helptext/";
    private static final String HELPTEXT_VIEW_NAME = BASE_PATH + "helptext";
    private static final String NEW_HELPTEXT_VIEW_NAME = BASE_PATH + "helptext_new";

    /* Key which identifies helpText payload in Model */
    public static final String HELPTEXT_MODEL_KEY = "helpText";
    private static final String HELPTEXTLIST_MODEL_KEY = "helpTexts";

    @RequestMapping(value = "/admin/helptext", method = RequestMethod.GET)
    public String ShowHelpTextPage(Model model) {

        try {

            List helpTexts = helpTextService.getAll();
            model.addAttribute(HELPTEXTLIST_MODEL_KEY, helpTexts);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.HELPTEXT_VIEW_NAME;

    }

    @RequestMapping(value = "/admin/helptext/new", method = RequestMethod.GET)
    public String ShowHelpTextNEwPage(ModelMap model) {
        HelpTextDto helptTextDto = new HelpTextDto();
        model.addAttribute(this.HELPTEXT_MODEL_KEY , helptTextDto);

        return this.NEW_HELPTEXT_VIEW_NAME;
    }

    @RequestMapping(value = "/admin/helptext", method = RequestMethod.POST)
    public String HelpTextPost(@ModelAttribute(HELPTEXT_MODEL_KEY) @Valid HelpTextDto helpTextDto
            , BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return HelpTextController.NEW_HELPTEXT_VIEW_NAME;
        }

        try {

            helpTextService.create(helpTextDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return HelpTextController.NEW_HELPTEXT_VIEW_NAME;
        }

        return "redirect:/admin/helptext";
    }

    //Used response body because ajax used to delete
    @RequestMapping(value = "/admin/helptext/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void DeleteHelpText(Model model,
                      @PathVariable("id") String id) {

        try {

            helpTextService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }
}