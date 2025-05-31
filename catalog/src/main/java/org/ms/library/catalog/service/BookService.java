package org.ms.library.catalog.service;

import org.ms.library.catalog.dto.BookDTO;
import org.ms.library.catalog.entity.Book;
import org.ms.library.catalog.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Page<BookDTO> getAllBooksPaged(Pageable pageable) {

        Page<Book> allBooksPaged = bookRepository.findAll(pageable);

        return allBooksPaged.map(BookDTO::new);

    }


}
