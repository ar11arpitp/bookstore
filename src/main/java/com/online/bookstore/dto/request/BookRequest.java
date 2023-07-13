package com.online.bookstore.dto.request;

import com.online.bookstore.common.constants.AppConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for Book APIs")
public class BookRequest {

    @NotNull(message = "name " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "name " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "Book Name", example = "The Alchemist")
    private String name;

    @Schema(description = "Book Description", example = "Novel by Paulo Coelho")
    private String description;

    @NotNull(message = "author " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "author " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "Author of The Book", example = "Paulo Coelho")
    private String author;

    @NotNull(message = "type " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "type " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The Type/Genere of the book", example = "Fiction")
    private String type;

    @NotNull(message = "price " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "price " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The price of the book", example = "27.99")
    private BigDecimal price;

    @NotNull(message = "isbn " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "isbn " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The ISBN-13 of the book", example = "978-0062355300")
    private String isbn;

    /**
     * Get the name of the book.
     *
     * @return The name of the book.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the book.
     *
     * @return The description of the book.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the author of the book.
     *
     * @return The author of the book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Get the type/classification of the book.
     *
     * @return The type/classification of the book.
     */
    public String getType() {
        return type;
    }

    /**
     * Get the price of the book.
     *
     * @return The price of the book.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Get the ISBN number of the book.
     *
     * @return The ISBN number of the book.
     */
    public String getIsbn() {
        return isbn;
    }
}