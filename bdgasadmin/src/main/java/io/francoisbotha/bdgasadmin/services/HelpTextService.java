package io.francoisbotha.bdgasadmin.services;

import io.francoisbotha.bdgasadmin.domain.dao.HelpTextRepository;
import io.francoisbotha.bdgasadmin.domain.dto.HelpTextDto;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.domain.model.HelpText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class HelpTextService  {

    @Autowired
    private HelpTextRepository helpTextRepository;

    public HelpText getHelpText(String id) throws EntityNotFoundException {
        HelpText helpText = helpTextRepository.findOneById(id);
        if(helpText == null){
            throw new EntityNotFoundException(HelpText.class, "id", id.toString());
        }
        return helpText;
    }

    public List getAll() {

        List helpTexts = new ArrayList();

        Iterable<HelpText> helpTextsIt = helpTextRepository.findAll();

        Iterator<HelpText> iter = helpTextsIt.iterator();

        while (iter.hasNext()) {
            helpTexts.add(iter.next());
        }

        return helpTexts;
    }

    public List getAll(String id) throws EntityNotFoundException  {

        List helpTexts = new ArrayList();

        Iterable<HelpText> helpTextsIt = helpTextRepository.findAllById(id);

        Iterator<HelpText> iter = helpTextsIt.iterator();

        while (iter.hasNext()) {
            helpTexts.add(iter.next());
        }

        if(helpTexts.isEmpty()
                || helpTexts.get(0) == null){
            throw new EntityNotFoundException(HelpText.class, "id", id.toString());
        }

        return helpTexts;
    }

    public HelpText create(HelpText helpText) {
        return helpTextRepository.save(helpText);
    }

    public HelpText update(String id, HelpTextDto helpTextDto) throws EntityNotFoundException {

        HelpText helpText = helpTextRepository.findOneById(id);

        helpText.setLang(helpTextDto.getLang());
        helpText.setName(helpTextDto.getName());
        helpText.setTxt(helpTextDto.getTxt());

        return helpTextRepository.save(helpText);
    }

    public void delete(String id)  throws EntityNotFoundException {

        HelpText helpText = helpTextRepository.findOneById(id);

        helpTextRepository.delete(helpText);
    }

}
