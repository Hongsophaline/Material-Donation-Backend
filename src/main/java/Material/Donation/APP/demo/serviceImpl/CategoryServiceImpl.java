package Material.Donation.APP.demo.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import Material.Donation.APP.demo.dto.request.CategoryRequest;
import Material.Donation.APP.demo.dto.response.CategoryResponse;
import Material.Donation.APP.demo.entity.Category;
import Material.Donation.APP.demo.repository.CategoryRepository;
import Material.Donation.APP.demo.service.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        categoryRepository.save(category);
        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // --- ADD THIS METHOD ---
    @Override
    public CategoryResponse getById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category with ID " + id + " not found"));
        return mapToResponse(category);
    }

    @Override
    public CategoryResponse update(UUID id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());
        categoryRepository.save(category);
        return mapToResponse(category);
    }

    @Override
    public void delete(UUID id) {
        // Good practice: check if it exists before deleting to avoid silent failures
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Category not found");
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse res = new CategoryResponse();
        res.setId(category.getId());
        res.setName(category.getName());
        return res;
    }
}