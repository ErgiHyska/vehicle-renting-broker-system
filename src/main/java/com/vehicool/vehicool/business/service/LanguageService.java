package com.vehicool.vehicool.business.service;

import com.vehicool.vehicool.persistence.entity.Language;
import com.vehicool.vehicool.persistence.repository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public Language getLanguageById(Long id) {
        return languageRepository.findById(id).orElse(null);
    }

    public Language save(Language language) {
        return languageRepository.save(language);
    }

    public void delete(Long id){
        languageRepository.deleteById(id);
    }

    public Language update(Language language,Long Id){
        language.setId(Id);
        return languageRepository.saveAndFlush(language);
    }


}
