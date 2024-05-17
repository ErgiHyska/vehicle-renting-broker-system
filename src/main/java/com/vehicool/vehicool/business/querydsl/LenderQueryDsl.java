package com.vehicool.vehicool.business.querydsl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.vehicool.vehicool.persistence.entity.QLender;
import com.vehicool.vehicool.persistence.entity.QVehicle;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class LenderQueryDsl implements QueryDsl<LenderFilter> {

    QLender qLender = QLender.lender;

    @Override
    public Predicate filter(LenderFilter filter) {
        BooleanBuilder query = new BooleanBuilder();



        if (StringUtils.hasText(filter.getFirstName())) {
            query.and(qLender.firstName.containsIgnoreCase(filter.getFirstName()));
        }
        if (StringUtils.hasText(filter.getLastName())) {
            query.and(qLender.lastName.containsIgnoreCase(filter.getLastName()));
        }
        if (StringUtils.hasText(filter.getEmail())) {
            query.and(qLender.email.containsIgnoreCase(filter.getEmail()));
        }
        if (StringUtils.hasText(filter.getPhoneNumber())) {
            query.and(qLender.phoneNumber.containsIgnoreCase(filter.getPhoneNumber()));
        }

        return query;
    }
}
