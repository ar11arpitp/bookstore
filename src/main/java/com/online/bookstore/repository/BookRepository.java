package com.online.bookstore.repository;
import com.online.bookstore.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaSpecificationExecutor<BookEntity>, org.springframework.data.jpa.repository.JpaRepository<BookEntity, Long> {
}
