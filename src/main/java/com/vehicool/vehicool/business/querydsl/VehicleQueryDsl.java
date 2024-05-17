package com.vehicool.vehicool.business.querydsl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.vehicool.vehicool.persistence.entity.QVehicle;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class VehicleQueryDsl implements QueryDsl<VehicleFilter> {

    QVehicle qVehicle = QVehicle.vehicle;

    @Override
    public Predicate filter(VehicleFilter filter) {
        BooleanBuilder query = new BooleanBuilder();

        if (StringUtils.hasText(filter.getBrand())) {
            query.and(qVehicle.Brand.containsIgnoreCase(filter.getBrand()));
        }
        if (StringUtils.hasText(filter.getColor())) {
            query.and(qVehicle.color.containsIgnoreCase(filter.getColor()));
        }
        if (StringUtils.hasText(filter.getType())) {
            query.and(qVehicle.type.containsIgnoreCase(filter.getType()));
        }
        if (StringUtils.hasText(filter.getModel())) {
            query.and(qVehicle.model.containsIgnoreCase(filter.getModel()));
        }
        if (StringUtils.hasText(filter.getTransmissionType())) {
            query.and(qVehicle.transmissionType.containsIgnoreCase(filter.getTransmissionType()));
        }
        if (StringUtils.hasText(filter.getEngineType())) {
            query.and(qVehicle.engineType.containsIgnoreCase(filter.getEngineType()));
        }
        if (filter.getEngineSize()!=null) {
            query.and(qVehicle.engineSize.eq(filter.getEngineSize()));
        }
        if (filter.getNoOfSeats()!=null) {
            query.and(qVehicle.noOfSeats.eq(filter.getNoOfSeats()));
        }
        if (filter.getAvailable()) {
            query.and(qVehicle.available);
        }
        if (filter.getProductionYear()!=null) {
            query.and(qVehicle.productionYear.eq(filter.getProductionYear()));
        }

        return query;
    }
}
