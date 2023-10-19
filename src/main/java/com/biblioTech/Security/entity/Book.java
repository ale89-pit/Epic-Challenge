package com.biblioTech.Security.entity;

import java.time.LocalDate;

import com.biblioTech.Enum.Category;
import com.biblioTech.Enum.Languages;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
	
	@Id
	@Column(length = 13, nullable = false)
    @Size(min = 10, max = 13)
	private String isbn;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String author;
	
	@Column(nullable = false)
	private String publisher;
	
	@Column(name = "published_year")
	private LocalDate publishedYear;
	
	@Enumerated(EnumType.STRING)
	public Category category;
	
	@Enumerated(EnumType.STRING)
	public Languages language;
	
	public String shelf;
	
	public String col;
	
	
	
	
}

