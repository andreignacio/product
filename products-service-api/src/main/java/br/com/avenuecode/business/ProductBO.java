package br.com.avenuecode.business;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.avenuecode.exceptions.BusinessException;
import br.com.avenuecode.model.Product;
import br.com.avenuecode.service.ProductService;

@Service
public class ProductBO {

	@Autowired
	private ProductService service;

	// define the logger
	private static final Logger log = LogManager.getLogger(ProductBO.class);

	public Product save(Product product) throws BusinessException {
		log.info("ProductBO save ");
		Product p = new Product();
		if (product == null) {
			throw new BusinessException("Product null can not be created.");
		}
		validateNotNullFields(product);

		p = service.save(product);
		return p;
	}

	public Product update(Product product) throws BusinessException {
		log.info("ProductBO update ");
		Product p = new Product();

		if (product != null) {
			validateNotNullFields(product);

			Product productsUpdate = service.findWithParentProductsAndImage(product.getId());
			if (productsUpdate != null && productsUpdate.getId() != null) {
				p = service.update(product);
			} else {
				throw new BusinessException("Product is not in data base yet.");
			}
		} else {
			throw new BusinessException("Product is not valid to be updated");
		}
		return p;
	}

	private void validateNotNullFields(Product product) throws BusinessException {
		if (product.getName() == null || product.getName().isEmpty()) {
			throw new BusinessException("The Product's name must be informated.");
		}
		if (product.getDescription() == null || product.getDescription().isEmpty()) {
			throw new BusinessException("The Product's description must be informated.");
		}
	}

	public void delete(Product product) throws BusinessException {
		log.info("ProductBO delete ");

		if (product != null) {
			Product productsDelete = service.findWithParentProductsAndImage(product.getId());
			if (productsDelete != null && productsDelete.getId() != null) {

				service.delete(productsDelete);
			} else {
				throw new BusinessException("Product is not in data base yet.");
			}
		} else {
			throw new BusinessException("Product is not in valid to delete");
		}

	}

	public List<Product> findAllProductsWithoutHierachical() {
		log.info("ProductBO findAllProductsWithoutHierachical");
		Iterable<Product> products = service.findAll();
		return Lists.newArrayList(products);
	}

	public List<Product> findAllWithParentProductsAndImage() {
		log.info("ProductBO findAllWithParentProductsAndImage");
		List<Product> products = service.findAllWithParentProductsAndImage();
		return products;
	}

	public List<Product> findAllWithParentProductsAndImage(Integer id) {
		log.info("ProductBO findAllWithParentProductsAndImage(Integer id) ");
		List<Product> products = service.findAllWithParentProductsAndImage(id);
		return products;
	}

	public Product findWithParentProductsAndImage(Integer id) {
		log.info("ProductBO findWithParentProductsAndImage(Integer id) ");
		Product products = service.findWithParentProductsAndImage(id);
		return products;
	}

	public List<Product> findAllByProduct(Integer id) {
		log.info("ProductBO findAllByProduct(Integer id) ");
		List<Product> products = service.findAllByProduct(id);
		return products;
	}

	public List<Product> findAllWithParentProducts(Integer id) {
		log.info("ProductBO findAllWithParentProducts(Integer id) ");
		List<Product> products = service.findAllWithParentProducts(id);
		return products;
	}

}
