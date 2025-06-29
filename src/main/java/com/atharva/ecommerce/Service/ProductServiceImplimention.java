package com.atharva.ecommerce.Service;

import com.atharva.ecommerce.Exception.ProductException;
import com.atharva.ecommerce.Model.Category;
import com.atharva.ecommerce.Model.Product;
import com.atharva.ecommerce.Repository.CategoryRepository;
import com.atharva.ecommerce.Repository.ProductRepository;
import com.atharva.ecommerce.Request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplimention implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private UserService userService;
    public ProductServiceImplimention(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel =categoryRepository.findByName(req.getTopLevelCategory());

        if(topLevel == null) {
            Category newTopLevel = new Category();
            newTopLevel.setName(req.getTopLevelCategory());
            newTopLevel.setLevel(1);

           topLevel =categoryRepository.save(newTopLevel);
        }

        Category secondLevel = categoryRepository
                .findByNameAndParent(req.getSecLevelCategory(), topLevel.getName());

        if (secondLevel == null) {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }

// Fetch or create third-level category
        Category thirdLevel = categoryRepository
                .findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());

        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();

        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountPrice(req.getDiscountedPrice());
        product.setDiscountPresent(req.getDiscountPresent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSizes());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

      Product savedProduct =  productRepository.save(product);
        System.out.println("product"+product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long id) throws ProductException {
        Product product=findProductById(id);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted Succesfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product=findProductById(productId);

        if(req.getQuantity()!=0) {
            product.setQuantity(req.getQuantity());
        }

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {

       Optional<Product> opt= productRepository.findById(id);
       if(opt.isPresent()) return opt.get();

       else throw new ProductException("Product not found");
    }

    @Override
    public List<Product> findProductsByCategory(String category) {

        List<Product> products= productRepository.findbyCategory(category);
        return products;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        /*
         pageNumber at which page user is
         pageSize total num of items display per page
         now if user is at page 2 and pageSize is 10 then now we need to take item from 20 index to 30 index
         like for page 0 -- 10 items for page 1 -- 10 items at page 2 -- 10 items
         10+10=20 will be now staring index
         just take subpart of list form 20 to 30 index and return
         for that
         Pageable pageable= PageRequest.of(pageNumber, pageSize); Helps Us
          it
          automatically calculate  from where we have to start now ie start index by using method pageable.getOffset();
          and we can get last end also by just startIndex+sizeofPage simple
          if end index is 40 but page size is only 30 just take page till 30

         */
        Pageable pageable= PageRequest.of(pageNumber, pageSize);

       List<Product> products= productRepository.filterProduct(category,minPrice,maxPrice,minDiscount,sort);

       if (!colors.isEmpty()){
          products= products.stream().filter(p-> colors.stream().anyMatch(c ->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
       }

       if(stock!=null) {
           if(stock.equals("in_stock")) {
              products= products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
           }

          else if (stock.equals("out_of_stock")) {
               products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
           }
       }



       int startIndex= (int) pageable.getOffset();
       int endIndex= Math.min( startIndex + pageable.getPageSize(), products.size());

       List<Product> pageContent= products.subList(startIndex, endIndex);
       Page<Product> filteredProducts=new PageImpl<>(pageContent,pageable,products.size());

       return filteredProducts;

    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
