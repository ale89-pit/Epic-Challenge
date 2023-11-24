package com.biblioTech.Security.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biblioTech.Security.entity.Library;
import java.util.List;
import com.biblioTech.Security.entity.Book;
import java.util.Map;


@Repository
public interface LibraryRepository extends JpaRepository<Library, Long>,PagingAndSortingRepository<Library, Long> {

	Page<Library> findAll(Pageable pageable);
	

	@Query(value ="SELECT l.*,\r\n"
			+ "  6371 * ACOS(\r\n"
			+ "    SIN(RADIANS(CAST(REPLACE(:latitudine, ',', '.') AS double precision))) * SIN(RADIANS(CAST(REPLACE(a.lat, ',', '.') AS double precision))) +\r\n"
			+ "    COS(RADIANS(CAST(REPLACE(:latitudine, ',', '.') AS double precision))) * COS(RADIANS(CAST(REPLACE(a.lat, ',', '.') AS double precision))) *\r\n"
			+ "    COS(RADIANS(CAST(REPLACE(a.lon, ',', '.') AS double precision)) - RADIANS(CAST(REPLACE(:longitudine, ',', '.') AS double precision)))\r\n"
			+ "  ) AS distance\r\n"
			+ "FROM library l\r\n"
			+ "JOIN address a ON l.address_id = a.id\r\n"
			+ "WHERE\r\n"
			+ "  6371 * ACOS(\r\n"
			+ "    SIN(RADIANS(CAST(REPLACE(:latitudine, ',', '.') AS double precision))) * SIN(RADIANS(CAST(REPLACE(a.lat, ',', '.') AS double precision))) +\r\n"
			+ "    COS(RADIANS(CAST(REPLACE(:latitudine, ',', '.') AS double precision))) * COS(RADIANS(CAST(REPLACE(a.lat, ',', '.') AS double precision))) *\r\n"
			+ "    COS(RADIANS(CAST(REPLACE(a.lon, ',', '.') AS double precision)) - RADIANS(CAST(REPLACE(:longitudine, ',', '.') AS double precision)))\r\n"
			+ "  ) <= 50\r\n"
			+ "ORDER BY distance;",nativeQuery= true)
	List<Library> findLibraryByGeolocalization(@Param("latitudine") String latitudine,@Param("longitudine") String lon);
	
	
//	List<Library>findLibraryWithKeyWord();
	
	
	Optional<Library> findByEmail(String email);

	Boolean existsByEmail(String email);

	//	SELECT * FROM library where id IN(SELECT library_id FROM library_books where isbn='9788867905348');
	//@Query("SELECT l FROM Library l JOIN l.booklist bl WHERE KEY(bl) = :isbn AND VALUE(bl)>0")
	@Query("SELECT l FROM Library l JOIN l.booklist b WHERE KEY(b) = :isbn AND VALUE(b) >0")
	List<Library> findBookAvaibleInLibrary(@Param("isbn") Book isbn);

}
