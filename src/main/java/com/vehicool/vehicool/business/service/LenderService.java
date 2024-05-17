package com.vehicool.vehicool.business.service;

import com.querydsl.core.types.Predicate;
import com.vehicool.vehicool.business.querydsl.LenderFilter;
import com.vehicool.vehicool.business.querydsl.LenderQueryDsl;
import com.vehicool.vehicool.business.querydsl.RenterQueryDsl;
import com.vehicool.vehicool.persistence.entity.Lender;
import com.vehicool.vehicool.persistence.repository.LenderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LenderService {
    private final LenderRepository lenderRepository;
    private final LenderQueryDsl lenderQueryDsl;

    public Lender getLenderById(Long id) {
        return lenderRepository.findById(id).orElse(null);
    }

    public Lender save(Lender lender) {
        return lenderRepository.save(lender);
    }

    public void delete(Long id){
        lenderRepository.deleteById(id);
    }

    public Lender update(Lender lender,Long Id){
        lender.setId(Id);
        return lenderRepository.saveAndFlush(lender);
    }


    public Page<Lender> findAll(LenderFilter lenderFilter, Pageable pageRequest) {
        Predicate filter = lenderQueryDsl.filter(lenderFilter);
        return lenderRepository.findAll(filter, pageRequest);
    }
}
