package com.dvsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dvsuperior.dscatalog.entities.Category;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {


}
