package com.vehicool.vehicool.business.service;

import com.vehicool.vehicool.persistence.entity.Administrator;
import com.vehicool.vehicool.persistence.repository.AdministratorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdministratorService {
    private final AdministratorRepository administratorRepository;

    public Administrator getAdministratorById(Long id) {
        return administratorRepository.findById(id).orElse(null);
    }

    public Administrator save(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public void delete(Long id){
        administratorRepository.deleteById(id);
    }

    public Administrator update(Administrator administrator,Long Id){
        administrator.setId(Id);
        return administratorRepository.saveAndFlush(administrator);
    }


}
