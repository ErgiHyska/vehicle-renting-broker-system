package com.vehicool.vehicool.persistence.repository;

import com.vehicool.vehicool.persistence.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<FileData,Integer> {
    Optional<FileData> findByName(String fileName);
    List<FileData> findAllByVehicleId(Long id);
}
