package com.dvsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dvsuperior.dscatalog.dto.CategoryDTO;
import com.dvsuperior.dscatalog.entities.Category;
import com.dvsuperior.dscatalog.repositories.CategoryRepository;
import com.dvsuperior.dscatalog.services.execeptions.ResourceEntityNotFoundException;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceEntityNotFoundException("Entity not found"));
        return new CategoryDTO(entity);
  
    }


}
