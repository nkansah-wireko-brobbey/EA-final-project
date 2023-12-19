package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.domain.AuditData;
import edu.miu.cs.cs544.domain.CustomError;
import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.Product;
import edu.miu.cs.cs544.domain.dto.ProductDTO;
import edu.miu.cs.cs544.domain.adapter.ProductAdapter;
import edu.miu.cs.cs544.repository.CustomerRepository;
import edu.miu.cs.cs544.repository.ProductRepository;
import edu.miu.cs.cs544.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, String email) {
        productDTO.setIsAvailable(true); //set the product to be available by default
        Customer customer = customerRepository.findByEmail(email);
        AuditData auditData = new AuditData();
        auditData.setCreatedBy(customer.getEmail());
        auditData.setUpdatedOn(LocalDateTime.now());
        auditData.setCreatedOn(LocalDateTime.now());
        auditData.setUpdatedBy(customer.getEmail());
        productDTO.setAuditData(auditData);
        return ProductAdapter.getProductDTO(productRepository.save(ProductAdapter.getProduct(productDTO)));
    }

    @Override
    public ProductDTO updateProduct(int id, ProductDTO productDTO) throws CustomError {
        productRepository.findById(id).orElseThrow(() -> new CustomError("Product with ID : " + id + " does not exist"));
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
                .orElseThrow(() -> new CustomError("Product with ID : " + id + " does not exist", HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductAdapter::getProductDTO).toList();
    }

    public List<ProductDTO> getAllAvailableProducts() {
        return productRepository.findAll().stream().filter(Product::getIsAvailable).map(ProductAdapter::getProductDTO).toList();
    }
}
