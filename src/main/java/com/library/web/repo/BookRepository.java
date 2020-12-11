package com.library.web.repo;

import com.library.web.models.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

   // List<Book> findByTitle(String Title);

}
