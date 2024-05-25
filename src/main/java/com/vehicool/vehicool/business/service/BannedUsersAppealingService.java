package com.vehicool.vehicool.business.service;

import com.vehicool.vehicool.persistence.entity.BannedUsersAppealing;
import com.vehicool.vehicool.persistence.entity.ConfidentialFile;
import com.vehicool.vehicool.persistence.entity.DataPool;
import com.vehicool.vehicool.persistence.repository.BannedUsersAppealingRepository;
import com.vehicool.vehicool.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BannedUsersAppealingService {
   private final BannedUsersAppealingRepository repository;
   public List<BannedUsersAppealing> list(){
       return  repository.findAll();
   }
   public BannedUsersAppealing updateAppeal(BannedUsersAppealing bannedUsersAppealing,Long id){
       bannedUsersAppealing.setId(id);
       return repository.saveAndFlush(bannedUsersAppealing);
   }
    public BannedUsersAppealing saveAppeal(BannedUsersAppealing bannedUsersAppealing){
        return repository.save(bannedUsersAppealing);
    }



}
