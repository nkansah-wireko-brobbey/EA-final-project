package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.domain.CustomError;
import edu.miu.cs.cs544.domain.Product;
import edu.miu.cs.cs544.domain.ProductDTO;
import edu.miu.cs.cs544.domain.adapter.ProductAdapter;
import edu.miu.cs.cs544.repository.ProductRepository;
import edu.miu.cs.cs544.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        return ProductAdapter.getProductDTO(productRepository.save(ProductAdapter.getProduct(productDTO)));
    }

    @Override
    public ProductDTO updateProduct(int id, ProductDTO productDTO) throws CustomError {
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomError("Product with ID : " + id + " does not exist"));
        return ProductAdapter.getProductDTO(productRepository.save(ProductAdapter.getProduct(productDTO)));
    }

    @Override
    public void deleteProduct(int id) throws CustomError {
        if (productRepository.findById(id).isEmpty())
            throw new CustomError("Product with ID : " + id + " does not exist");
        else
            productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getProduct(int id) throws CustomError {
        return ProductAdapter.getProductDTO(productRepository.findById(id)
                .orElseThrow(() -> new CustomError("Product with ID : " + id + " does not exist")));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductAdapter::getProductDTO).toList();
    }
    public List<ProductDTO> getAllAvailableProducts() {
        return productRepository.findAll().stream().filter(Product::getIsAvailable).map(ProductAdapter::getProductDTO).toList();
    }
}
