package com.vehicool.vehicool.business.service;

import com.querydsl.core.types.Predicate;
import com.vehicool.vehicool.business.querydsl.LenderFilter;
import com.vehicool.vehicool.business.querydsl.LenderQueryDsl;
import com.vehicool.vehicool.persistence.entity.ConfidentialFile;
import com.vehicool.vehicool.persistence.entity.Contract;
import com.vehicool.vehicool.persistence.entity.Lender;
import com.vehicool.vehicool.persistence.repository.DatabaseStorageRepository;
import com.vehicool.vehicool.persistence.repository.LenderRepository;
import com.vehicool.vehicool.util.fileconfigs.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LenderService {
    private final LenderRepository lenderRepository;
    private final LenderQueryDsl lenderQueryDsl;
    private DatabaseStorageRepository databaseStorageRepository;

    public Lender getLenderById(Long id) {
        return lenderRepository.findById(id).orElse(null);
    }

    public Lender save(Lender lender) {
        return lenderRepository.save(lender);
    }

    public void delete(Long id) {
        lenderRepository.deleteById(id);
    }

    public Lender update(Lender lender, Long Id) {
        lender.setId(Id);
        return lenderRepository.saveAndFlush(lender);
    }


    public Page<Lender> findAll(LenderFilter lenderFilter, Pageable pageRequest) {
        Predicate filter = lenderQueryDsl.filter(lenderFilter);
        return lenderRepository.findAll(filter, pageRequest);
    }

    public List<Contract> contractRequests(Long lenderId, Long statusId) {
        return lenderRepository.contractRequests(lenderId, statusId);
    }

    @Transactional
    public String uploadLenderConfidentialFile(List<MultipartFile> file, Long lenderId) throws IOException {
        Lender lender = getLenderById(lenderId);
        if (lender == null) {
            return "Failed to upload! Wrong lender ID";
        }
        List<ConfidentialFile> list = new ArrayList<>();
        for(MultipartFile current:file){
            ConfidentialFile confidentialFile =ConfidentialFile.builder().name(current.getOriginalFilename()).type(current.getContentType()).lender(lender).imageData(ImageUtils.compressImage(current.getBytes())).build();
            list.add(confidentialFile);
        }
        databaseStorageRepository.saveAll(list);
        if (!list.isEmpty()) {
            return "file uploaded successfully !";
        }
        return null;
    }

    public List<byte[]> getLenderConfidentialFiles(Long lenderId) {
        Lender lender = getLenderById(lenderId);
        List<byte[]> images = new ArrayList<>();
        List<ConfidentialFile> lenderConfidentialFiled=lender.getConfidentialFiles();
        for(ConfidentialFile currentFile:lenderConfidentialFiled){
            byte[] image = ImageUtils.decompressImage(currentFile.getImageData());
            images.add(image);
        }
        return images;
    }
}
