package br.com.avenuecode.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import br.com.avenuecode.business.ProductBO;
import br.com.avenuecode.error.ApiError;
import br.com.avenuecode.exceptions.BusinessException;
import br.com.avenuecode.model.Image;
import br.com.avenuecode.model.Product;
import br.com.avenuecode.model.dto.ImageTO;
import br.com.avenuecode.model.dto.ProductNoHierachicalTO;
import br.com.avenuecode.model.dto.ProductRequest;
import br.com.avenuecode.model.dto.ProductTO;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	private ProductBO productBO;

	private ModelMapper modelMapper = new ModelMapper();

	@GetMapping(value = "/all/hierarchy")
	public ResponseEntity<List<ProductTO>> getAllHierarchical() {
		List<ProductTO> productsTO = null;
		List<Product> products = productBO.findAllWithParentProductsAndImage();

		if (products == null || products.isEmpty()) {
			return new ResponseEntity<List<ProductTO>>(productsTO, HttpStatus.NO_CONTENT);
		}
		productsTO = toProductTOList(products);

		ResponseEntity<List<ProductTO>> responseEntity = new ResponseEntity<>(productsTO, HttpStatus.OK);

		return responseEntity;
	}

	@GetMapping("/all")
	public ResponseEntity<List<ProductNoHierachicalTO>> getAll() {
		List<ProductNoHierachicalTO> productsTO = null;
		List<Product> products = productBO.findAllProductsWithoutHierachical();

		if (products == null || products.isEmpty()) {
			return new ResponseEntity<List<ProductNoHierachicalTO>>(productsTO, HttpStatus.NO_CONTENT);
		}
		productsTO = toProductNoHierachicalTOList(products);

		ResponseEntity<List<ProductNoHierachicalTO>> responseEntity = new ResponseEntity<>(productsTO, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<ProductTO>> getById(@PathVariable("id") Integer id) {
		List<ProductTO> productsTO = null;
		List<Product> products = productBO.findAllByProduct(id);

		if (products == null || products.isEmpty()) {
			return new ResponseEntity<List<ProductTO>>(productsTO, HttpStatus.NO_CONTENT);
		}
		productsTO = toProductTOList(products);

		ResponseEntity<List<ProductTO>> responseEntity = new ResponseEntity<>(productsTO, HttpStatus.OK);

		return responseEntity;
	}

	@GetMapping("/parent/{id}")
	public ResponseEntity<List<ProductTO>> getProductsByParentId(@PathVariable("id") Integer id) {
		List<ProductTO> productsTO = null;
		List<Product> products = productBO.findAllWithParentProducts(id);

		if (products == null || products.isEmpty()) {
			return new ResponseEntity<List<ProductTO>>(productsTO, HttpStatus.NO_CONTENT);
		}
		productsTO = toProductTOList(products);

		ResponseEntity<List<ProductTO>> responseEntity = new ResponseEntity<>(productsTO, HttpStatus.OK);

		return responseEntity;
	}

	@RequestMapping(method = { RequestMethod.POST }, produces = { "application/json" }, consumes = {
			"application/json" })
	public ResponseEntity<Object> save(@RequestBody ProductRequest productWrapper) {
		Product product = null;
		Product ret = new Product();
		try {
			product = modelMapper.map(productWrapper, Product.class);
			ret = productBO.save(product);
		} catch (BusinessException e) {
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "error occurred");
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}

		ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(ret, HttpStatus.CREATED);
		return responseEntity;
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestBody ProductRequest productWrapper) {
		Product product = null;
		Product ret = new Product();
		try {
			product = modelMapper.map(productWrapper, Product.class);
			ret = productBO.update(product);
		} catch (BusinessException e) {
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "error occurred");
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}

		ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(ret, HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestBody ProductRequest productWrapper) {
		Product product = null;
		try {
			product = modelMapper.map(productWrapper, Product.class);
			productBO.delete(product);
		} catch (BusinessException e) {
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "error occurred");
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}

		ResponseEntity<Object> responseEntity = new ResponseEntity<Object>("Deleted product", HttpStatus.OK);
		return responseEntity;
	}

	private List<ProductTO> toProductTOList(List<Product> products) {
		List<ProductTO> productsTO = new ArrayList<ProductTO>();
		for (Product product : products) {
			ProductTO to = new ProductTO();
			to.setId(product.getId());
			to.setName(product.getName());
			to.setDescription(product.getDescription());
			to.setParent(modelMapper.map(product, ProductTO.class));
			Set<Product> children = product.getChildren();
			List<ProductTO> childrenTO = new ArrayList<>();
			for (Product productChild : children) {
				ProductTO toChild = modelMapper.map(productChild, ProductTO.class);
				childrenTO.add(toChild);
			}
			to.setChildren(childrenTO);
			Set<Image> images = product.getImages();
			List<ImageTO> imagesTO = new ArrayList<>();
			for (Image image : images) {
				ImageTO imageTO = modelMapper.map(image, ImageTO.class);
				imagesTO.add(imageTO);
			}
			to.setImages(imagesTO);
			productsTO.add(to);
		}
		return productsTO;
	}

	private List<ProductNoHierachicalTO> toProductNoHierachicalTOList(List<Product> products) {
		List<ProductNoHierachicalTO> productsTO = new ArrayList<ProductNoHierachicalTO>();
		for (Product product : products) {
			ProductNoHierachicalTO to = modelMapper.map(product, ProductNoHierachicalTO.class);
			productsTO.add(to);
		}
		return productsTO;
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
