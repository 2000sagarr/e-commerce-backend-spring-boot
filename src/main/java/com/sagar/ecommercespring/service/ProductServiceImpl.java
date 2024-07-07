package com.sagar.ecommercespring.service;

import com.sagar.ecommercespring.exception.ProductException;
import com.sagar.ecommercespring.model.Category;
import com.sagar.ecommercespring.model.Product;
import com.sagar.ecommercespring.repository.CategoryRepository;
import com.sagar.ecommercespring.repository.ProductRespository;
import com.sagar.ecommercespring.request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRespository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRespository productRespository, CategoryRepository categoryRepository) {
        this.productRepository = productRespository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest req) throws ProductException {
        Category topLevel = categoryRepository.findByName(req.getTopLavelCategory());
        if(topLevel == null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLavelCategory());
            topLevelCategory.setLevel(1);
            topLevel = categoryRepository.save(topLevelCategory);
        }

        Category secondLevel=categoryRepository.findByNameAndParant(req.getSecondLavelCategory(),topLevel.getName());
        if(secondLevel==null) {

            Category secondLavelCategory=new Category();
            secondLavelCategory.setName(req.getSecondLavelCategory());
            secondLavelCategory.setParentCategory(topLevel);
            secondLavelCategory.setLevel(2);

            secondLevel= categoryRepository.save(secondLavelCategory);
        }

        Category thirdLevel=categoryRepository.findByNameAndParant(req.getThirdLavelCategory(),secondLevel.getName());
        if(thirdLevel==null) {

            Category thirdLavelCategory=new Category();
            thirdLavelCategory.setName(req.getThirdLavelCategory());
            thirdLavelCategory.setParentCategory(secondLevel);
            thirdLavelCategory.setLevel(3);

            thirdLevel=categoryRepository.save(thirdLavelCategory);
        }
        Product product=new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPersent(req.getDiscountPersent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product Deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        Product reqproduct=findProductById(productId);

        if(product.getQuantity()!=0) {
            reqproduct.setQuantity(product.getQuantity());
        }
        if(product.getDescription()!=null) {
            reqproduct.setDescription(product.getDescription());
        }

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt=productRepository.findById(id);

        if(opt.isPresent()) {
            return opt.get();
        }
        throw new ProductException("product not found with id "+id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {

        return productRepository.findByCategory(category);    }

    @Override
    public List<Product> searchProduct(String query) {
        return productRepository.searchProduct(query);
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);


        if (!colors.isEmpty()) {
            products = products.stream()
                    .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .toList();
        }

        if(stock!=null) {

            if(stock.equals("in_stock")) {
                products=products.stream().filter(p->p.getQuantity()>0).toList();
            }
            else if (stock.equals("out_of_stock")) {
                products=products.stream().filter(p->p.getQuantity()<1).toList();
            }


        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
        List<Product> pageContent = products.subList(startIndex, endIndex);
        return new PageImpl<>(pageContent, pageable, products.size()); // If color list is empty, do nothing and return all products
    }

    @Override
    public List<Product> recentlyAddedProduct() {

        return productRepository.findTop10ByOrderByCreatedAtDesc();
    }

}
