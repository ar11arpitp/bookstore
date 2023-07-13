package com.online.bookstore.controller;

import com.online.bookstore.dto.request.BookRequest;
import com.online.bookstore.dto.request.BookSearchSpecification;
import com.online.bookstore.dto.response.BookResponse;
import com.online.bookstore.dto.response.PaginationResponse;
import com.online.bookstore.dto.response.SuccessResponse;
import com.online.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;

    private BookRequest bookRequest;
    private BookResponse bookResponse;


    @BeforeEach
    void setUp() {
        bookRequest = BookRequest.builder()
                .name("3 mistakes of my life")
                .description("A novel by Chetan Bhagat")
                .author("Chetan Bhagat")
                .type("Fiction")
                .price(new BigDecimal(9.99))
                .isbn("9-213-16-1234-0")
                .build();

        bookResponse = BookResponse.builder()
                .id(1L)
                .name("The Alchemist")
                .description("A novel by" )
                .author("Auther")
                .type("Fiction")
                .price(28.99)
                .isbn("921-3-16-17989-0")
                .build();
    }

    @Test
    void testAddBook() {
        Mockito.when(bookService.addBook(bookRequest)).thenReturn(bookResponse);
        ResponseEntity<SuccessResponse<BookResponse>> result = bookController.addBook(bookRequest);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testAddListOfBooks() {
        ResponseEntity<SuccessResponse<List<BookResponse>>> result = bookController.addListOfBooks(List.of(bookRequest));
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testGetById() {
        ResponseEntity<SuccessResponse<BookResponse>> result = bookController.getById(Long.valueOf(1));
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testFilter() {
        ResponseEntity<PaginationResponse<BookResponse>> result = bookController.filter(new BookSearchSpecification(1L, "name", "description", "author", "type", BigDecimal.valueOf(0), "isbn"), null);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}