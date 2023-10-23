package com.biblioTech.Security.payload;

import java.time.LocalDate;

import com.biblioTech.Enum.Category;
import com.biblioTech.Enum.Languages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
	private String isbn;
	private String title;
	private String author;
	private String publisher;
	private LocalDate publishedYear;
	public Category category;
	public Languages language;

}
