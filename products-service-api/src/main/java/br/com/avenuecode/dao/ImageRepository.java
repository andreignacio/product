package br.com.avenuecode.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.avenuecode.model.Image;


public interface ImageRepository extends JpaRepository<Image, Integer> {
	
	@Query("select i from Image i join fetch i.product where i.product.id = :pProduct")
	public List<Image> findAllByProduct(@Param("pProduct") Integer id);

}
