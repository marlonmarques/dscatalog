package com.dvsuperior.dscatalog.services;

import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dvsuperior.dscatalog.dto.ProductDTO;
import com.dvsuperior.dscatalog.entities.Category;
import com.dvsuperior.dscatalog.entities.Product;
import com.dvsuperior.dscatalog.repositories.CategoryRepository;
import com.dvsuperior.dscatalog.repositories.ProductRepository;
import com.dvsuperior.dscatalog.services.execeptions.DatabaseException;
import com.dvsuperior.dscatalog.services.execeptions.ResourceEntityNotFoundException;
import com.dvsuperior.dscatalog.tests.Factory;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
//@Transactional
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private Category category;


   @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = Factory.createProduct();
        category = Factory.createCategory();
        page = new PageImpl<>(List.of(product));
        
        Mockito.when(repository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
        Mockito.when(repository.findById(existingId)).thenReturn(java.util.Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(java.util.Optional.empty());
        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(ResourceEntityNotFoundException.class);
        Mockito.when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        Mockito.when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(ResourceEntityNotFoundException.class);
        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        // Configurar o comportamento dos mocks
        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        Page<ProductDTO> result = service.findAllPaged(pageable);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        ProductDTO dto = service.findById(existingId);
        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceEntityNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {
        ProductDTO dto = Factory.createProductDTO();
        ProductDTO result = service.update(existingId, dto);
        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        ProductDTO dto = Factory.createProductDTO();
        Assertions.assertThrows(ResourceEntityNotFoundException.class, () -> {
            service.update(nonExistingId, dto);
        });
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceEntityNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }
    
    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
    }


}
