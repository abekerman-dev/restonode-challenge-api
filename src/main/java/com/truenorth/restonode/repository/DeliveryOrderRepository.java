package com.truenorth.restonode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truenorth.restonode.model.DeliveryOrder;

public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
	
}
