package com.giulidev.commerce.services;

import com.giulidev.commerce.dto.ProductDTO;
import com.giulidev.commerce.entities.Product;
import com.giulidev.commerce.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductsRepository repository;

    // BUSCAR REGISTROS 🔎
    // ****************************************************************************************
    // 🔢 POR ID
    @Transactional(readOnly = true)
    public ProductDTO findByID(Long id) {
        Product product = repository.findById(id).get();
        return new ProductDTO(product);
    }
    // 💯 TODOS (PAGINADOS)
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> productList = repository.findAll(pageable);
        return productList.map(x -> new ProductDTO(x));
    }
    // ******************************************************************************************

    // CRIANDO NOVO REGISTRO 📝
    // ******************************************************************************************
    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        product = repository.save(product);
        return new ProductDTO(product);
    }
    // ******************************************************************************************

    // ATUALIZANDO REGISTRO 🛠️
    // ******************************************************************************************
    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
       // crio uma referência do produto pelo ID que veio - preparo o objeto
        // ele é monitorado pela JPA
       Product product = repository.getReferenceById(id);

       // altero esse produto com os dados do DTO que veio
        copyDtoToEntity(product, productDTO);

       // salvo esse novo produto com o objeto referenciado
       product = repository.save(product);

       // retorno pro controller o DTO alterado
       return new ProductDTO(product);
    }
    // ******************************************************************************************

    // METODO AUXILIAR PARA CONVERSAO DE DTO PARA PRODUCT 🔁
    private void copyDtoToEntity (Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());
    }

}
