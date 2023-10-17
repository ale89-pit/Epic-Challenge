package com.biblioTech.Security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "municipality")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Municipality {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(nullable = true)
    String province_id;
    
    @Column
    String municipality_id;
    
    @Column(nullable = true)
    String name;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="province")
    private Province provincename;

}
