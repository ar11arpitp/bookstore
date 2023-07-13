package com.online.bookstore.service.impl;

import com.online.bookstore.dto.response.ShoppingCartResponse;
import com.online.bookstore.entity.ShoppingCart;
import com.online.bookstore.entity.User;
import com.online.bookstore.enums.CartStatusEnums;
import com.online.bookstore.repository.ShoppingCartRepository;
import com.online.bookstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @Mock
    private ShoppingCartRepository mockShoppingCartRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private ModelMapper mockModelMapper;

    private ShoppingCartServiceImpl shoppingCartServiceImplUnderTest;
    private User user;
    @BeforeEach
    void setUp() {
        shoppingCartServiceImplUnderTest = new ShoppingCartServiceImpl(mockShoppingCartRepository, mockUserService,
                mockModelMapper);
        List<String> authorities = new ArrayList<String>();
        authorities.add("User");
        authorities.add("Admin");
        user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setAuthorities(authorities);
    }

    @Test
    void testCreateCart() {
        // Setup
        final ShoppingCartResponse expectedResult = ShoppingCartResponse.builder().build();

        when(mockUserService.retrieveCurrentLoginUser()).thenReturn(user);

        // Configure ShoppingCartRepository.save(...).
        final ShoppingCart shoppingCart = ShoppingCart.builder()
                .user(User.builder()
                        .username("username")
                        .build())
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build();
        when(mockShoppingCartRepository.save(ShoppingCart.builder()
                .user(User.builder()
                        .username("username")
                        .build())
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build())).thenReturn(shoppingCart);

        when(mockModelMapper.map(ShoppingCart.builder()
                .user(User.builder()
                        .username("username")
                        .build())
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build(), ShoppingCartResponse.class)).thenReturn(ShoppingCartResponse.builder().build());

        // Run the test
        final ShoppingCartResponse result = shoppingCartServiceImplUnderTest.createCart();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetUserCart() {
        // Setup
        final ShoppingCartResponse expectedResult = ShoppingCartResponse.builder().build();
        when(mockUserService.retrieveCurrentLoginUser()).thenReturn(user);

        // Configure ShoppingCartRepository.findByUserAndActiveIsTrue(...).
        final Optional<ShoppingCart> shoppingCart = Optional.of(ShoppingCart.builder()
                .user(user)
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build());
        when(mockShoppingCartRepository.findByUserAndActiveIsTrue(User.builder()
                .username("username")
                .build())).thenReturn(shoppingCart);

        when(mockModelMapper.map(ShoppingCart.builder()
                .user(User.builder()
                        .username("username")
                        .build())
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build(), ShoppingCartResponse.class)).thenReturn(ShoppingCartResponse.builder().build());

        // Run the test
        final ShoppingCartResponse result = shoppingCartServiceImplUnderTest.getUserCart();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetUserCart_ShoppingCartRepositoryReturnsAbsent() {
        when(mockUserService.retrieveCurrentLoginUser()).thenReturn(user);

        when(mockShoppingCartRepository.findByUserAndActiveIsTrue(User.builder()
                .username("username")
                .build())).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> shoppingCartServiceImplUnderTest.getUserCart())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testGetUserCartEntity() {
        // Setup
        when(mockUserService.retrieveCurrentLoginUser()).thenReturn(user);
        final ShoppingCart expectedResult = ShoppingCart.builder()
                .user(User.builder()
                        .username("username")
                        .build())
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build();

        when(mockUserService.retrieveCurrentLoginUser()).thenReturn(user);

        // Configure ShoppingCartRepository.findByUserAndActiveIsTrue(...).
        final Optional<ShoppingCart> shoppingCart = Optional.of(ShoppingCart.builder()
                .user(user)
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build());
        when(mockShoppingCartRepository.findByUserAndActiveIsTrue(User.builder()
                .username("username")
                .build())).thenReturn(shoppingCart);

        // Run the test
        final ShoppingCart result = shoppingCartServiceImplUnderTest.getUserCartEntity();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetUserCartEntity_ShoppingCartRepositoryReturnsAbsent() {
        when(mockUserService.retrieveCurrentLoginUser()).thenReturn(user);

        when(mockShoppingCartRepository.findByUserAndActiveIsTrue(User.builder()
                .username("username")
                .build())).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> shoppingCartServiceImplUnderTest.getUserCartEntity())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testRetrieveActiveCartForUser() {

        final ShoppingCart expectedResult = ShoppingCart.builder()
                .user(User.builder()
                        .username("username")
                        .build())
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build();

        // Configure ShoppingCartRepository.findByUserAndActiveIsTrue(...).
        final Optional<ShoppingCart> shoppingCart = Optional.of(ShoppingCart.builder()
                .user(User.builder()
                        .username("username")
                        .build())
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build());
        when(mockShoppingCartRepository.findByUserAndActiveIsTrue(User.builder()
                .username("username")
                .build())).thenReturn(shoppingCart);

        // Run the test
        final ShoppingCart result = shoppingCartServiceImplUnderTest.retrieveActiveCartForUser(user);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testRetrieveActiveCartForUser_ShoppingCartRepositoryReturnsAbsent() {
        // Setup
        when(mockShoppingCartRepository.findByUserAndActiveIsTrue(User.builder()
                .username("username")
                .build())).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> shoppingCartServiceImplUnderTest.retrieveActiveCartForUser(user))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testUpdate() {
        // Setup
        final ShoppingCart shoppingCart = ShoppingCart.builder()
                .user(User.builder()
                        .username("username")
                        .build())
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build();

        // Run the test
        shoppingCartServiceImplUnderTest.update(shoppingCart);

        // Verify the results
        verify(mockShoppingCartRepository).save(ShoppingCart.builder()
                .user(User.builder()
                        .username("username")
                        .build())
                .active(false)
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .build());
    }
}
