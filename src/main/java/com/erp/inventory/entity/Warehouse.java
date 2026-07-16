package com.erp.inventory.entity;

import com.erp.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "warehouses", schema = "inventory")
public class Warehouse extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 255)
    private String location;
}
