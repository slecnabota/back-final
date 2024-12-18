package lab5.iitu.controller;

import lab5.iitu.dto.CategoriesDto;
import lab5.iitu.entity.Categories;
import lab5.iitu.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoriesRepository categoriesRepository;

    @GetMapping
    List<CategoriesDto> get() {
        return categoriesRepository.findAll().stream()
                .map(category -> new CategoriesDto(category.getId(), category.getCategory()))
                .toList();
    }

    @PostMapping
    void create(@RequestBody CategoriesDto dto) {
        categoriesRepository.save(new Categories(null, dto.category()));
    }
}
