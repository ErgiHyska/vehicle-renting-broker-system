package com.vehicool.vehicool.business.service;


import com.querydsl.core.types.Predicate;
import com.vehicool.vehicool.business.querydsl.RenterFilter;
import com.vehicool.vehicool.business.querydsl.RenterQueryDsl;
import com.vehicool.vehicool.persistence.entity.Renter;
import com.vehicool.vehicool.persistence.repository.RenterRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RenterService {
    private final RenterRepository renterRepository;

    private final RenterQueryDsl renterQueryDsl;

    public Renter getRenterById(Long id) {
        return renterRepository.findById(id).orElse(null);
    }

    public Renter save(Renter renter) {
        return renterRepository.save(renter);
    }

    public void delete(Long id){
        renterRepository.deleteById(id);
    }

    public Renter update(Renter renter,Long Id){
        renter.setId(Id);
        return renterRepository.saveAndFlush(renter);
    }


    public Page<Renter> findAll(RenterFilter renterFilter, Pageable pageRequest) {
        Predicate filter = renterQueryDsl.filter(renterFilter);
        return renterRepository.findAll(filter, pageRequest);
    }
}
