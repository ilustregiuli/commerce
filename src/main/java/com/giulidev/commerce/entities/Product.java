package com.giulidev.commerce.entities;

import com.giulidev.commerce.dto.ProductDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // configuração para um texto LONGO
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private String imgUrl;

    // Relacionamento Muitos (M) x Muitos (N)
    // Esse atributo é uma coleção (SET) de elementos da outra tabela
    @ManyToMany  // identifica o relacionamento "M x N"
    @JoinTable(name = "tb_product_category",
                joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "category_id"))
    // Faz a junção com a tabela "N", criando uma tabela intermediaria
    // Referencia a coluna "id" dessa tabela "M" com a coluna "id" da
    //  tabela "N". Na outra tabela, "N", só é necessário identificar o
    //  relacionamento e indicar esse atributo aqui com a referência
    private Set<Category> categories = new HashSet<>();

    // A partir do produto, verificar quais os OrderItens associados a eles
    @OneToMany(mappedBy = "id.product")
    private Set<OrderItem> itens = new HashSet<>();

    public Product() {}

    public Product(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Product(ProductDTO productDTO) {
        this.setName(productDTO.getName());
        this.setDescription(productDTO.getDescription());
        this.setPrice(productDTO.getPrice());
        this.setImgUrl(productDTO.getImgUrl());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Set<OrderItem> getItems() {
        return itens;
    }
    public List<Order> getOrders() {
        return itens.stream().map(x -> x.getOrder()).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
