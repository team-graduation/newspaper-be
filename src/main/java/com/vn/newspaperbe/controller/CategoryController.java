package com.vn.newspaperbe.controller;

import com.vn.newspaperbe.entity.Category;
import com.vn.newspaperbe.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createNewCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Category>> getAllCategory() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Integer id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        return categoryOptional.map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        return categoryOptional.map(category1 -> {
            category.setCategoryId(category1.getCategoryId());
            return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Integer id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        return categoryOptional.map(category -> {
            categoryService.delete(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}