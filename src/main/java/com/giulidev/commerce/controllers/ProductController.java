package com.giulidev.commerce.controllers;


import com.giulidev.commerce.dto.ProductDTO;
import com.giulidev.commerce.entities.Product;
import com.giulidev.commerce.repositories.ProductsRepository;
import com.giulidev.commerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // BUSCAR REGISTROS POR ID E TODOS (PAGINADOS)
    // ****************************************************************************************

    /* ---- SEM USO DO RESPONSE ENTITY
    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        return productService.findByID(id);
    } */

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO productDTO = productService.findByID(id);
        // Envio da reposta personalizada (OK) e envio do DTO no corpo da resposta
        return ResponseEntity.ok(productDTO);
    }

    /* ---- SEM USO DO RESPONSE ENTITY
    @GetMapping
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productService.findAll(pageable);
    } */

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }
    // ******************************************************************************************

    // CRIANDO NOVO REGISTRO
    // ******************************************************************************************
    /* ---- SEM USO DO RESPONSE ENTITY
    @PostMapping
    public ProductDTO insert(@RequestBody ProductDTO productDTO) {
        return productService.insert(productDTO);
    } */
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDTO) {
        // Salva o produto e devolve o resultado na mesma referência
        productDTO = productService.insert(productDTO);

        // personalizando resposta para recurso criado - metodo ".created"
        // o metodo ".created" espera receber uma URI de parâmetro
        // essa URI é o link para o recurso criado

        // criação da URI 🔽
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productDTO.getId())
                .toUri();

        // retorna a resposta 🔽 com a URI do objeto criado com o objeto no corpo
        return ResponseEntity.created(uri).body(productDTO);
    }
    // ******************************************************************************************

    // ATUALIZANDO REGISTRO
    // ******************************************************************************************
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        productDTO = productService.update(id, productDTO);
        // Envio da reposta personalizada (OK) e envio do DTO no corpo da resposta
        return ResponseEntity.ok(productDTO);
    }
    // ******************************************************************************************

}
