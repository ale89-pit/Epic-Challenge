package com.biblioTech.Security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Library;
import java.util.List;
import com.biblioTech.Security.entity.Book;
import java.util.Map;


@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

	Optional<Library> findByEmail(String email);

	Boolean existsByEmail(String email);

	//	SELECT * FROM library where id IN(SELECT library_id FROM library_books where isbn='9788867905348');
	//@Query("SELECT l FROM Library l JOIN l.booklist bl WHERE KEY(bl) = :isbn AND VALUE(bl)>0")
	@Query("SELECT l FROM Library l JOIN l.booklist b WHERE KEY(b) = :isbn")
	List<Library> findBookAvaibleInLibrary(@Param("isbn") Book isbn);

}
