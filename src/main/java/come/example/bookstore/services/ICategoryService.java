package come.example.bookstore.services;

import come.example.bookstore.dto.requests.CategoryRequest;
import come.example.bookstore.dto.responses.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id) throws Exception;
    CategoryResponse addCategory(CategoryRequest categoryRequest) throws Exception;
    CategoryResponse updateCategory(long id, CategoryRequest categoryRequest) throws Exception;
    void deleteCategory(long id) throws Exception;
}
