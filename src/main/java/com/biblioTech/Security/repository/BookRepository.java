package com.biblioTech.Security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Book;
import java.util.List;
import com.biblioTech.Enum.Category;


@Repository
public interface BookRepository extends JpaRepository<Book, String>,PagingAndSortingRepository<Book,String>{
	
	Page<Book> findAll(Pageable pageable);
	Page<Book> findByCategory(Category category,Pageable pageable);

	
}
