package br.com.avenuecode.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.avenuecode.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("select distinct p from Product p left join fetch p.images i left join fetch p.parent pa left join fetch pa.children order by p.name, i.id")
    public List<Product> findAllWithParentProductsAndImage();

    @Query("select distinct p from Product p left join fetch p.parent pa left join fetch pa.children left join fetch p.images where p.id = :pProduct ")
    public List<Product> findAllWithParentProductsAndImage(@Param("pProduct") Integer id);

    @Query("select distinct p from Product p left join fetch p.images left join fetch p.parent pa left join fetch pa.children where p.id = :pProduct")
    public Product findOneWithParentAndImage(@Param("pProduct") Integer id);

    @Query("select distinct p from Product p where p.id = :pProduct")
    public List<Product> findAllByProduct(@Param("pProduct") Integer id);

    @Query("select distinct p from Product p join fetch p.parent where p.parent.id = :pProduct")
    public List<Product> findAllWithParentProducts(@Param("pProduct") Integer id);

    @Query("select distinct p from Product p order by p.name")
    public List<Product> findAll();

    public List<Product> findAllDistinctById(Integer id);




}
