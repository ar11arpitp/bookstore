package com.online.bookstore.service;

import com.online.bookstore.dto.request.BookRequest;
import com.online.bookstore.dto.request.BookSearchSpecification;
import com.online.bookstore.dto.response.BookResponse;
import com.online.bookstore.dto.response.PaginationResponse;
import com.online.bookstore.entity.BookEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    BookResponse addBook(BookRequest bookRequest);

    List<BookResponse> addListOfBooks(List<BookRequest> bookRequestList);

    BookResponse getById(Long bookId);

    PaginationResponse<BookResponse> getBooks(BookSearchSpecification bookSearchSpecification, Pageable pageable);

    BookEntity findById(Long bookId);
}
