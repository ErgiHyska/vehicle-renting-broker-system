package com.vehicool.vehicool.persistence.repository;

import com.vehicool.vehicool.persistence.entity.RenterReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterReviewRepository extends JpaRepository<RenterReview,Long> {

}
