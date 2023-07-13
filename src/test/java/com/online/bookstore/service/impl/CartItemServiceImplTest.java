package com.online.bookstore.service.impl;

import com.online.bookstore.common.enums.SuccessCodeEnums;
import com.online.bookstore.common.util.CalculationUtils;
import com.online.bookstore.dto.request.CartItemRequest;
import com.online.bookstore.dto.response.BookResponse;
import com.online.bookstore.dto.response.CartItemResponse;
import com.online.bookstore.entity.BookEntity;
import com.online.bookstore.entity.CartItem;
import com.online.bookstore.entity.ShoppingCart;
import com.online.bookstore.entity.User;
import com.online.bookstore.exception.handler.NotFoundCustomException;
import com.online.bookstore.repository.CartItemRepository;
import com.online.bookstore.service.BookService;
import com.online.bookstore.service.ShoppingCartService;
import com.online.bookstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {

    @Mock
    private ShoppingCartService mockShoppingCartService;
    @Mock
    private CartItemRepository mockCartItemRepository;
    @Mock
    private CalculationUtils mockCalculationUtils;
    @Mock
    private BookService mockBookService;
    @Mock
    private UserService mockUserService;
    @Mock
    private ModelMapper mockModelMapper;

    private User user;
    private ShoppingCart shoppingCart;
    private BookEntity book;
    private CartItem cartItem;
    private CartItemRequest cartItemRequest;
    private BookResponse bookResponse;

    private CartItemServiceImpl cartItemServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        cartItemServiceImplUnderTest = new CartItemServiceImpl(mockShoppingCartService, mockCartItemRepository,
                mockCalculationUtils, mockBookService, mockUserService, mockModelMapper);
        List<String> authorities = new ArrayList<String>();
        authorities.add("User");
        authorities.add("Admin");
        user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setAuthorities(authorities);

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setItems(new ArrayList<>());

        book = BookEntity.builder()
                .id(1L)
                .name("ABC")
                .description("A novel by TEST")
                .author("TEST AUTHOR")
                .type("fiction")
                .price(15.99)
                .isbn("0978-3-16-49932-0")
                .build();

        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(2);

        cartItemRequest = new CartItemRequest();
        cartItemRequest.setBookId(1L);
        cartItemRequest.setQuantity(4);

        bookResponse = BookResponse.builder()
                .id(1L)
                .name("ABC")
                .description("A novel by TEST")
                .author("TEST AUTHOR")
                .type("fiction")
                .price(15.99)
                .isbn("0978-3-16-49932-0")
                .build();
    }

    @Test
    void testAddBookToCart() {
        // Setup
        final CartItemRequest cartItemRequest = new CartItemRequest();
        cartItemRequest.setBookId(0L);
        cartItemRequest.setQuantity(0);
        when(mockUserService.retrieveCurrentLoginUser()).thenReturn(user);
        // Configure BookService.findById(...).
        final BookEntity bookEntity = BookEntity.builder()
                .price(0.0)
                .id(null)
                .build();
        when(mockBookService.findById(0L)).thenReturn(bookEntity);

        when(mockShoppingCartService.retrieveActiveCartForUser(any())).thenReturn(shoppingCart);
        // Run the test
        final String result = cartItemServiceImplUnderTest.addBookToCart(cartItemRequest);

        // Verify the results
        assertThat(result).isEqualTo("Added to cart successfully.");
    }

    @Test
    void testRemoveBookFromCart_CartItemRepositoryFindByShoppingCartAndBookReturnsAbsent() {
        // Setup
        // Configure BookService.findById(...).
        final BookEntity bookEntity = BookEntity.builder()
                .price(0.0)
                .id(null)
                .build();
        when(mockBookService.findById(0L)).thenReturn(bookEntity);

        // Configure UserService.retrieveCurrentLoginUser(...).
        final User user = User.builder()
                .username("username")
                .id(null)
                .build();
        when(mockUserService.retrieveCurrentLoginUser()).thenReturn(user);

        // Configure ShoppingCartService.retrieveActiveCartForUser(...).
        final ShoppingCart shoppingCart = ShoppingCart.builder()
                .items(List.of(CartItem.builder()
                        .book(BookEntity.builder()
                                .price(0.0)
                                .id(null)
                                .build())
                        .quantity(0)
                        .totalPrice(new BigDecimal("0.00"))
                        .id(null)
                        .build()))
                .id(null)
                .build();

        // Run the test
        assertThatThrownBy(() -> cartItemServiceImplUnderTest.removeBookFromCart(0L, 0))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void testRemoveBookFromCart_WithQuantity() {
        // Mocking dependencies
        Long bookId = 123L;
        Integer quantity = 2;

        BookEntity book = BookEntity.builder()
                .price(0.0)
                .id(1L)
                .build();
        when(mockUserService.retrieveCurrentLoginUser()).thenReturn(user);
        when(mockBookService.findById(any(Long.class))).thenReturn(book);
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem1 = CartItem.builder()
                .book(BookEntity.builder()
                        .price(0.0)
                        .id(1L)
                        .build())
                .quantity(0)
                .totalPrice(new BigDecimal("0.00"))
                .id(1L)
                .build();
        cartItems.add(cartItem1);
        final ShoppingCart shoppingCart = ShoppingCart.builder()
                .items(cartItems)
                .id(null)
                .build();
        when(mockShoppingCartService.retrieveActiveCartForUser(user)).thenReturn(shoppingCart);
        String result = cartItemServiceImplUnderTest.removeBookFromCart(1L,2);


        // Verifying the behavior
        assertEquals(SuccessCodeEnums.DELETED_CART_ITEM_SUCCESS.getMessage(), result);
    }

    @Test
    public void testRemoveBookFromCart_WithoutQuantity() {
        Long bookId = 123L;
        Integer quantity = 2;

        BookEntity book = BookEntity.builder()
                .price(0.0)
                .id(1L)
                .build();
        when(mockUserService.retrieveCurrentLoginUser()).thenReturn(user);
        when(mockBookService.findById(any(Long.class))).thenReturn(book);
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem1 = CartItem.builder()
                .book(BookEntity.builder()
                        .price(0.0)
                        .id(1L)
                        .build())
                .quantity(0)
                .totalPrice(new BigDecimal("0.00"))
                .id(1L)
                .build();
        cartItems.add(cartItem1);
        final ShoppingCart shoppingCart = ShoppingCart.builder()
                .items(cartItems)
                .id(null)
                .build();
        when(mockShoppingCartService.retrieveActiveCartForUser(user)).thenReturn(shoppingCart);
        when(mockCartItemRepository.findByShoppingCartAndBook(any(ShoppingCart.class),any(BookEntity.class))).thenReturn(Optional.of(cartItem1));
        String result = cartItemServiceImplUnderTest.removeBookFromCart(1L,null);

        // Verifying the behavior
        assertEquals("ID: 1 Deleted Successfully.", result);
    }

    @Test
    void testUpdateCartItemQuantity() {
        // Setup
        final CartItemResponse expectedResult = CartItemResponse.builder()
                .book(BookResponse.builder().build())
                .quantity(0)
                .build();

        // Configure CartItemRepository.findById(...).
        final Optional<CartItem> cartItem = Optional.of(CartItem.builder()
                .book(BookEntity.builder()
                        .price(0.0)
                        .id(null)
                        .build())
                .shoppingCart(ShoppingCart.builder()
                        .items(List.of())
                        .id(null)
                        .build())
                .quantity(0)
                .totalPrice(new BigDecimal("0.00"))
                .id(null)
                .build());
        when(mockCartItemRepository.findById(0L)).thenReturn(cartItem);

        when(mockModelMapper.map(BookEntity.builder()
                .price(0.0)
                .id(null)
                .build(), BookResponse.class)).thenReturn(BookResponse.builder().build());

        // Run the test
        final CartItemResponse result = cartItemServiceImplUnderTest.updateCartItemQuantity(0L, 0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockCartItemRepository).save(CartItem.builder()
                .book(BookEntity.builder()
                        .price(0.0)
                        .id(null)
                        .build())
                .shoppingCart(ShoppingCart.builder()
                        .items(List.of())
                        .id(null)
                        .build())
                .quantity(0)
                .totalPrice(new BigDecimal("0.00"))
                .id(null)
                .build());
    }

    @Test
    void testUpdateCartItemQuantity_CartItemRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockCartItemRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> cartItemServiceImplUnderTest.updateCartItemQuantity(0L, 0))
                .isInstanceOf(NotFoundCustomException.class);
    }
}
