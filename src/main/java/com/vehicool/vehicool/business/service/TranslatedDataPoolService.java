package com.vehicool.vehicool.business.service;


import com.vehicool.vehicool.persistence.entity.TranslatedDataPool;
import com.vehicool.vehicool.persistence.repository.TranslatedDataPoolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TranslatedDataPoolService {
    private final TranslatedDataPoolRepository translatedDataPoolRepository;

    public TranslatedDataPool getTranslatedDataPoolById(Long id) {
        return translatedDataPoolRepository.findById(id).orElse(null);
    }

    public TranslatedDataPool save(TranslatedDataPool translatedDataPool) {
        return translatedDataPoolRepository.save(translatedDataPool);
    }

    public void delete(Long id){
        translatedDataPoolRepository.deleteById(id);
    }

    public TranslatedDataPool update(TranslatedDataPool translatedDataPool,Long Id){
        translatedDataPool.setId(Id);
        return translatedDataPoolRepository.saveAndFlush(translatedDataPool);
    }


}
