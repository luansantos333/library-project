package org.ms.library.catalog.controller;

import org.ms.library.catalog.dto.BookDTO;
import org.ms.library.catalog.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/catalog/book")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> findAllBooks (Pageable pageable) {

        Page<BookDTO> books = service.getAllBooksPaged(pageable);

        return ResponseEntity.ok(books);
    }


}
