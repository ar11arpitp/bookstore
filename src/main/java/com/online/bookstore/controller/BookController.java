package com.online.bookstore.controller;

import com.online.bookstore.dto.request.BookRequest;
import com.online.bookstore.dto.request.BookSearchSpecification;
import com.online.bookstore.dto.response.BookResponse;
import com.online.bookstore.dto.response.PaginationResponse;
import com.online.bookstore.dto.response.SuccessResponse;
import com.online.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@Tag(name = "Books Store", description = "API Operations to manage the Books")
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * Add a new book to the system.
     *
     * @param bookRequest The book details to be added.
     * @return ResponseEntity containing a success response with the added book.
     */
    @PostMapping("/addBook")
    @Operation(summary = "Add a New Book", security = {@SecurityRequirement(name = "basicAuth")})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SuccessResponse<BookResponse>> addBook(@Valid @RequestBody BookRequest bookRequest) {
        BookResponse addedBook = bookService.addBook(bookRequest);

        SuccessResponse<BookResponse> response = SuccessResponse.<BookResponse>builder()
                .data(addedBook)
                .message("Book added successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Add a list of books to the system.
     *
     * @param bookRequestList The list of book details to be added.
     * @return ResponseEntity containing a success response with the added books.
     */
    @PostMapping("/addBookDetails")
    @Operation(summary = "Add a List of Books", security = {@SecurityRequirement(name = "basicAuth")})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SuccessResponse<List<BookResponse>>> addListOfBooks(@Valid @RequestBody List<BookRequest> bookRequestList) {
        List<BookResponse> addedBooks = bookService.addListOfBooks(bookRequestList);

        SuccessResponse<List<BookResponse>> response = SuccessResponse.<List<BookResponse>>builder()
                .data(addedBooks)
                .message("Books added successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Fetch a book by its ID.
     *
     * @param bookId The ID of the book to fetch.
     * @return ResponseEntity containing a success response with the fetched book.
     */
    @GetMapping("/{bookId}")
    @Operation(summary = "Fetch a Book by Book Id", security = {@SecurityRequirement(name = "basicAuth")})
    public ResponseEntity<SuccessResponse<BookResponse>> getById(
            @Parameter(name = "bookId", in = ParameterIn.PATH, required = true)
            @PathVariable Long bookId) {
        BookResponse fetchedBook = bookService.getById(bookId);

        SuccessResponse<BookResponse> response = SuccessResponse.<BookResponse>builder()
                .data(fetchedBook)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * Fetch all books based on filtering criteria.
     *
     * @param bookSearchSpecification The filtering criteria for the books.
     * @param pageable                The pagination information.
     * @return ResponseEntity containing a page of filtered books.
     */
    @PostMapping("/searchBooks")
    @Operation(summary = "Fetch All Books", security = {@SecurityRequirement(name = "basicAuth")})
    @PageableAsQueryParam
    public ResponseEntity<PaginationResponse<BookResponse>> filter(
            @RequestBody BookSearchSpecification bookSearchSpecification,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        PaginationResponse<BookResponse> filteredBooks = bookService.getBooks(bookSearchSpecification, pageable);

        return ResponseEntity.ok(filteredBooks);
    }

}
