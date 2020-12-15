package com.library.web.repo;

import com.library.web.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorContainingOrTitleAllIgnoreCaseContaining(String author, String title);

}
