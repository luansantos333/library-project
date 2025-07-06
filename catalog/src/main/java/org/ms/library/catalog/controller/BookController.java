package org.ms.library.catalog.controller;

import org.ms.library.catalog.dto.BookCategoriesDTO;
import org.ms.library.catalog.dto.BookDTO;
import org.ms.library.catalog.repository.projection.BookCategoriesProjection;
import org.ms.library.catalog.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/catalog/book")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> findAllBooks(Pageable pageable) {

        Page<BookDTO> books = service.getAllBooksPaged(pageable);

        return ResponseEntity.ok(books);
    }

    @GetMapping("/categories/{title}")
    public ResponseEntity<Page<BookCategoriesProjection>> getAllBookCategories(@PathVariable(name = "title") String title,
                                                                               @RequestParam(name = "author", required = false) String author,
                                                                               @RequestParam(name = "categories", required = false) Set<String> categories,
                                                                               Pageable pageable) {
        Page<BookCategoriesProjection> allBooksAndCategories = service.getAllBooksAndCategories(title, author, categories, pageable);
        return ResponseEntity.ok(allBooksAndCategories);

    }


    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findOneBook(@PathVariable(name = "id") Long id) {

        BookDTO bookById = service.getBookById(id);

        return ResponseEntity.ok(bookById);
    }

    @PostMapping
    public ResponseEntity<BookCategoriesDTO> saveBook(@RequestBody BookCategoriesDTO bookDTO) {

        BookCategoriesDTO book = service.createBook(bookDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bookDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(book);

    }

    @PutMapping("/{id}")
    public ResponseEntity<BookCategoriesDTO> updateBook(@PathVariable(name = "id") Long id, @RequestBody BookCategoriesDTO bookDTO) {

        BookCategoriesDTO bookCategoriesDTO = service.updateBook(id, bookDTO);
        return ResponseEntity.ok(bookCategoriesDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "id") Long id) {

        service.deleteBookById(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookCategoriesDTO> patchBook(@PathVariable(name = "id") Long id, @RequestParam(required = true, name = "amount") Integer amount) {

        BookCategoriesDTO bookCategoriesDTO = service.increaseBookStock(id, amount);

        return ResponseEntity.ok(bookCategoriesDTO);


    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<BookDTO>> findBooksByIds(@RequestParam(name = "ids", required = true) List<Long> ids) {

        List<BookDTO> booksByListOfIds = service.getBooksByListOfIds(ids);

        return ResponseEntity.ok(booksByListOfIds);

    }

}
