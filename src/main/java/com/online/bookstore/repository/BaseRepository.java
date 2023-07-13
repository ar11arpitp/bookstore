package com.online.bookstore.repository;

import com.online.bookstore.entity.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends GenericEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
