package com.dvsuperior.dscatalog.services;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dvsuperior.dscatalog.dto.ProductDTO;
import com.dvsuperior.dscatalog.repositories.ProductRepository;
import com.dvsuperior.dscatalog.services.execeptions.ResourceEntityNotFoundException;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIT {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private Long totalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 3L;
        totalProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists() {
        service.delete(existingId);
        Assertions.assertEquals(totalProducts - 1, repository.count());
    }

    @Test
    public void deleteShouldThrowResourceEntityNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceEntityNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void findAllPageShouldReturnPageWhenPage0Size10() {
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        Page<ProductDTO> result = service.findAllPaged(pageable);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(totalProducts, result.getTotalElements());
    }   

    @Test
    public void findAllPageShouldReturnEmptyPageWhenPageDoesNotExist() {
        Pageable pageable = Pageable.ofSize(10).withPage(50);

        Page<ProductDTO> result = service.findAllPaged(pageable);
        Assertions.assertTrue(result.isEmpty());
    }   

    @Test
    public void findAllPageShouldReturnOrderbyNameWhenSortByName() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

        Page<ProductDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
        Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName()); 
        Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
    }


}
