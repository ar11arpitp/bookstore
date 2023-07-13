package com.online.bookstore.service.impl;

import com.online.bookstore.common.enums.ErrorCodeEnums;
import com.online.bookstore.dto.request.BookRequest;
import com.online.bookstore.dto.request.BookSearchSpecification;
import com.online.bookstore.dto.response.BookResponse;
import com.online.bookstore.dto.response.PaginationResponse;
import com.online.bookstore.entity.BookEntity;
import com.online.bookstore.exception.handler.NotFoundCustomException;
import com.online.bookstore.repository.BookRepository;
import com.online.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    /**
     * Adds a book.
     *
     * @param bookRequest The book request.
     * @return The added book response.
     */
    @Override
    public BookResponse addBook(BookRequest bookRequest) {
        bookRequest.setType(bookRequest.getType().toLowerCase());
        BookEntity bookEntity = modelMapper.map(bookRequest, BookEntity.class);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return modelMapper.map(savedBookEntity, BookResponse.class);
    }

    /**
     * Adds a list of books.
     *
     * @param bookRequestList The list of book requests.
     * @return The list of added book responses.
     */
    @Override
    public List<BookResponse> addListOfBooks(List<BookRequest> bookRequestList) {
        log.info("Inside addListOfBooks {}", bookRequestList);
        return bookRequestList.stream()
                .map(this::addBook)
                .toList();
    }

    /**
     * Gets a book by ID.
     *
     * @param bookId The ID of the book.
     * @return The book response.
     */
    @Override
    public BookResponse getById(Long bookId) {
        BookEntity bookEntity = findById(bookId);
        BookResponse bookResponse = modelMapper.map(bookEntity, BookResponse.class);
        log.info("Received Book {}", bookResponse);
        return bookResponse;
    }

    /**
     * Gets a paginated list of books based on the search criteria.
     *
     * @param bookSearchSpecification The book search specification.
     * @param pageable                The pageable object.
     * @return The paginated list of book responses.
     */
    @Override
    public PaginationResponse<BookResponse> getBooks(BookSearchSpecification bookSearchSpecification, Pageable pageable) {
        Page<BookEntity> page = bookRepository.findAll(bookSearchSpecification, pageable);
        List<BookResponse> bookResponses = modelMapper.map(page.getContent(), new TypeToken<List<BookResponse>>() {}.getType());
        log.info("List of the books received {}", bookResponses);
        return PaginationResponse.<BookResponse>builder()
                .data(bookResponses)
                .numberOfPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .pageNumber(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .build();
    }

    /**
     * Finds a book by ID.
     *
     * @param bookId The ID of the book.
     * @return The found book entity.
     * @throws NotFoundCustomException If the book is not found.
     */
    @Override
    public BookEntity findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundCustomException(
                        ErrorCodeEnums.NOT_FOUND_ERR.getErrorCode(),
                        String.format(ErrorCodeEnums.NOT_FOUND_ERR.getMessage(), bookId)));
    }
}
