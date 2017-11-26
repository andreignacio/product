package br.com.avenuecode.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.avenuecode.dao.ProductRepository;
import br.com.avenuecode.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public Product save(Product product) {
		Product p = repository.save(product);
		return p;
	}

	public void save(List<Product> product) {
		repository.save(product);
	}

	public Product update(Product product) {
		Product p = repository.save(product);
		return p;
	}

	public void delete(Product product) {

		repository.delete(product);
	}

	public List<Product> findAll() {
		Iterable<Product> products = repository.findAll();
		return Lists.newArrayList(products);
	}

	public List<Product> findAllWithParentProductsAndImage() {
		List<Product> products = repository.findAllWithParentProductsAndImage();
		return products;
	}

	public List<Product> findAllWithParentProductsAndImage(Integer id) {
		List<Product> products = repository.findAllWithParentProductsAndImage(id);
		return products;
	}

	public Product findWithParentProductsAndImage(Integer id) {
		Product products = repository.findOneWithParentAndImage(id);
		return products;
	}

	public List<Product> findAllByProduct(Integer id) {
		List<Product> products = repository.findAllByProduct(id);
		return products;
	}

	public List<Product> findAllWithParentProducts(Integer id) {
		List<Product> products = repository.findAllWithParentProducts(id);
		return products;
	}

	public Product findOne(Integer id) {
		Product products = repository.findOne(id);
		return products;
	}

}
