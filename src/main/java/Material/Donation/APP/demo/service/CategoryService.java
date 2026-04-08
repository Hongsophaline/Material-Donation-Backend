package Material.Donation.APP.demo.service;

import java.util.List;
import java.util.UUID;
import Material.Donation.APP.demo.dto.request.CategoryRequest;
import Material.Donation.APP.demo.dto.response.CategoryResponse;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    List<CategoryResponse> getAll();

    // Add this method to fetch a single category by its ID
    CategoryResponse getById(UUID id);

    CategoryResponse update(UUID id, CategoryRequest request);

    void delete(UUID id);
}