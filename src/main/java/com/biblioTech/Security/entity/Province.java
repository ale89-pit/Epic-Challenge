package com.biblioTech.Security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "province")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Province {
	
	@Id
    String sign;
	
    @Column(nullable = false)
    String name;
    
    @Column(nullable = false)
    String region;
}
