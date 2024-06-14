package come.example.bookstore.services.impl;

import come.example.bookstore.dto.requests.CategoryRequest;
import come.example.bookstore.dto.responses.CategoryResponse;
import come.example.bookstore.models.entities.Book;
import come.example.bookstore.models.entities.Category;
import come.example.bookstore.repositories.BookRepository;
import come.example.bookstore.repositories.CategoryRepository;
import come.example.bookstore.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryResponse.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new Exception("Category not found."));
        return modelMapper.map(category, CategoryResponse.class);
    }

    @Override
    @Transactional
    public CategoryResponse addCategory(CategoryRequest categoryRequest) throws Exception {
        if(categoryRepository.existsByName(categoryRequest.getName())){
            throw new Exception("Category exists.");
        }
        Category category = Category.builder().name(categoryRequest.getName()).build();
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryResponse.class);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(long id, CategoryRequest categoryRequest) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new Exception("Category not found with id: "+ id));
        category.setName(categoryRequest.getName());
        return modelMapper.map(categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    @Transactional
    public void deleteCategory(long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new Exception("Category not found with id: "+ id));
        for(Book book: category.getBooks()){
            book.getCategories().remove(category);
            bookRepository.save(book);
        }
        categoryRepository.delete(category);
    }
}
