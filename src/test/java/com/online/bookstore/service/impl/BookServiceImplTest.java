package com.online.bookstore.service.impl;

import com.online.bookstore.dto.request.BookRequest;
import com.online.bookstore.dto.request.BookSearchSpecification;
import com.online.bookstore.dto.response.BookResponse;
import com.online.bookstore.dto.response.PaginationResponse;
import com.online.bookstore.entity.BookEntity;
import com.online.bookstore.exception.handler.NotFoundCustomException;
import com.online.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Disabled
@SuppressWarnings(value = "checked")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository mockBookRepository;
    @Mock
    private ModelMapper mockModelMapper;

    private BookServiceImpl bookServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        bookServiceImplUnderTest = new BookServiceImpl(mockBookRepository, mockModelMapper);
    }

    @Test
    @SuppressWarnings(value = "checked")
    void testAddBook() {
        // Setup
        final BookRequest bookRequest = BookRequest.builder()
                .type("type")
                .build();
        final BookResponse expectedResult = BookResponse.builder().build();
        when(mockModelMapper.map(BookRequest.builder()
                .type("type")
                .build(), BookEntity.class)).thenReturn(BookEntity.builder().build());
        when(mockBookRepository.save(BookEntity.builder().build())).thenReturn(BookEntity.builder()
                .id(1L)
                .name("ABC")
                .description("A novel by TEST")
                .author("TEST AUTHOR")
                .type("fiction")
                .price(15.99)
                .isbn("0978-3-16-49932-0")
                .build());

        // Run the test
        final BookResponse result = bookServiceImplUnderTest.addBook(bookRequest);
    }

    @Test
    @SuppressWarnings(value = "checked")
    void testAddListOfBooks() {
        // Setup
        final List<BookRequest> bookRequestList = List.of(BookRequest.builder()
                .type("type")
                .build());
        final List<BookResponse> expectedResult = List.of(BookResponse.builder().build());
        when(mockModelMapper.map(BookRequest.builder()
                .type("type")
                .build(), BookEntity.class)).thenReturn(BookEntity.builder().build());
       BookEntity entity = BookEntity.builder()
               .id(1L)
               .name("ABC")
               .description("A novel by TEST")
               .author("TEST AUTHOR")
               .type("fiction")
               .price(15.99)
               .isbn("0978-3-16-49932-0")
               .build();
        when(mockBookRepository.save(BookEntity.builder().build())).thenReturn(entity);
        // Run the test
        final List<BookResponse> result = bookServiceImplUnderTest.addListOfBooks(bookRequestList);
    }

    @Test
    void testGetById() {
        // Setup
        final BookResponse expectedResult = BookResponse.builder().build();

        // Configure BookRepository.findById(...).
        final Optional<BookEntity> bookEntity = Optional.of(BookEntity.builder().build());
        when(mockBookRepository.findById(0L)).thenReturn(bookEntity);

        when(mockModelMapper.map(BookEntity.builder().build(), BookResponse.class))
                .thenReturn(BookResponse.builder().build());

        // Run the test
        final BookResponse result = bookServiceImplUnderTest.getById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetById_BookRepositoryReturnsAbsent() {
        // Setup
        when(mockBookRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> bookServiceImplUnderTest.getById(0L)).isInstanceOf(NotFoundCustomException.class);
    }

    @Test
    void testGetBooks() {
        // Setup
        final BookSearchSpecification bookSearchSpecification = new BookSearchSpecification();
        bookSearchSpecification.setId(0L);
        bookSearchSpecification.setName("name");
        bookSearchSpecification.setDescription("description");
        bookSearchSpecification.setAuthor("author");
        bookSearchSpecification.setType("type");

        final PaginationResponse<Object> expectedResult = PaginationResponse.builder()
                .totalElements(0L)
                .data(List.of())
                .numberOfPages(0)
                .pageSize(0)
                .pageNumber(0)
                .build();

        // Configure BookRepository.findAll(...).
        final Page<BookEntity> bookEntities = new PageImpl<>(List.of(BookEntity.builder().build()));
        final BookSearchSpecification spec = new BookSearchSpecification();
        spec.setId(0L);
        spec.setName("name");
        spec.setDescription("description");
        spec.setAuthor("author");
        spec.setType("type");
        when(mockBookRepository.findAll(eq(spec), any(Pageable.class))).thenReturn(bookEntities);

        when(mockModelMapper.map(eq(List.of(BookEntity.builder().build())), any(Type.class)))
                .thenReturn(List.of(BookResponse.builder().build()));

        // Run the test
        final PaginationResponse<BookResponse> result = bookServiceImplUnderTest.getBooks(bookSearchSpecification,
                PageRequest.of(0, 1));
    }

    @Test
    void testGetBooks_BookRepositoryReturnsNoItems() {
        // Setup
        final BookSearchSpecification bookSearchSpecification = new BookSearchSpecification();
        bookSearchSpecification.setId(0L);
        bookSearchSpecification.setName("name");
        bookSearchSpecification.setDescription("description");
        bookSearchSpecification.setAuthor("author");
        bookSearchSpecification.setType("type");

        final PaginationResponse<Object> expectedResult = PaginationResponse.builder()
                .totalElements(0L)
                .data(List.of(new BookResponse()))
                .numberOfPages(1)
                .pageSize(1)
                .pageNumber(1)
                .build();

        // Configure BookRepository.findAll(...).
        final BookSearchSpecification spec = new BookSearchSpecification();
        spec.setId(0L);
        spec.setName("name");
        spec.setDescription("description");
        spec.setAuthor("author");
        spec.setType("type");
        when(mockBookRepository.findAll(eq(spec), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        when(mockModelMapper.map(any(List.class), any(Type.class)))
                .thenReturn(List.of(BookResponse.builder().build()));

        // Run the test
        final PaginationResponse<BookResponse> result = bookServiceImplUnderTest.getBooks(bookSearchSpecification,
                PageRequest.of(0, 1));

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetBooks_ModelMapperReturnsNoItems() {
        // Setup
        final BookSearchSpecification bookSearchSpecification = new BookSearchSpecification();
        bookSearchSpecification.setId(0L);
        bookSearchSpecification.setName("name");
        bookSearchSpecification.setDescription("description");
        bookSearchSpecification.setAuthor("author");
        bookSearchSpecification.setType("type");

        final PaginationResponse<Object> expectedResult = PaginationResponse.builder()
                .totalElements(1L)
                .data(List.of())
                .numberOfPages(1)
                .pageSize(1)
                .pageNumber(1)
                .build();

        // Configure BookRepository.findAll(...).
        final Page<BookEntity> bookEntities = new PageImpl<>(List.of(BookEntity.builder().build()));
        final BookSearchSpecification spec = new BookSearchSpecification();
        spec.setId(0L);
        spec.setName("name");
        spec.setDescription("description");
        spec.setAuthor("author");
        spec.setType("type");
        when(mockBookRepository.findAll(eq(spec), any(Pageable.class))).thenReturn(bookEntities);

        when(mockModelMapper.map(eq(List.of(BookEntity.builder().build())), any(Type.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final PaginationResponse<BookResponse> result = bookServiceImplUnderTest.getBooks(bookSearchSpecification,
                PageRequest.of(0, 1));

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindById() {
        // Setup
        final BookEntity expectedResult = BookEntity.builder().build();

        // Configure BookRepository.findById(...).
        final Optional<BookEntity> bookEntity = Optional.of(BookEntity.builder().build());
        when(mockBookRepository.findById(0L)).thenReturn(bookEntity);

        // Run the test
        final BookEntity result = bookServiceImplUnderTest.findById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindById_BookRepositoryReturnsAbsent() {
        // Setup
        when(mockBookRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> bookServiceImplUnderTest.findById(0L)).isInstanceOf(NotFoundCustomException.class);
    }
}
