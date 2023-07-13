package com.online.bookstore.dto.request;

import com.online.bookstore.entity.BookEntity;
import com.online.bookstore.common.constants.AppConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Book filter specification request")
public class BookSearchSpecification implements Specification<BookEntity> {

    @Schema(description = "Book ID", example = "1")
    private Long id;

    @Schema(description = "Book name", example = "The Lord of the Rings.")
    private String name;

    @Schema(description = "Book description", example = "The Lord of the Rings. by J. R. R. Tolkien.")
    private String description;

    @Schema(description = "Book author", example = "J. R. R. Tolkien.")
    private String author;

    @Schema(description = "Book type/genre", example = "Fantasy")
    private String type;

    @Schema(description = "Book price", example = "21.99")
    private BigDecimal price;

    @Schema(description = "Book ISBN", example = "9780590353403")
    private String isbn;

    @Override
    public Predicate toPredicate(Root<BookEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (id != null)
            predicates.add(criteriaBuilder.like(root.get("id").as(String.class),
                    AppConstants.Characters.PERCENTAGE + id + AppConstants.Characters.PERCENTAGE));

        if (isbn != null)
            predicates.add(criteriaBuilder.like(root.get("isbn").as(String.class),
                    AppConstants.Characters.PERCENTAGE + isbn + AppConstants.Characters.PERCENTAGE));

        if (price != null)
            predicates.add(criteriaBuilder.like(root.get("price").as(String.class),
                    AppConstants.Characters.PERCENTAGE + price + AppConstants.Characters.PERCENTAGE));

        if (name != null)
            predicates.add(criteriaBuilder.like(root.get("name").as(String.class),
                    AppConstants.Characters.PERCENTAGE + name + AppConstants.Characters.PERCENTAGE));

        if (author != null)
            predicates.add(criteriaBuilder.like(root.get("author").as(String.class),
                    AppConstants.Characters.PERCENTAGE + author + AppConstants.Characters.PERCENTAGE));

        if (description != null)
            predicates.add(criteriaBuilder.like(root.get("description").as(String.class),
                    AppConstants.Characters.PERCENTAGE + description + AppConstants.Characters.PERCENTAGE));

        if (type != null)
            predicates.add(criteriaBuilder.like(root.get("type").as(String.class),
                    AppConstants.Characters.PERCENTAGE + type + AppConstants.Characters.PERCENTAGE));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
