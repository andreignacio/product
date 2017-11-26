package br.com.avenuecode.productsserviceapi;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.avenuecode.constants.ImageType;
import br.com.avenuecode.error.ApiError;
import br.com.avenuecode.model.Product;
import br.com.avenuecode.model.dto.ImageTO;
import br.com.avenuecode.model.dto.ProductNoHierachicalTO;
import br.com.avenuecode.model.dto.ProductParentTO;
import br.com.avenuecode.model.dto.ProductRequest;
import br.com.avenuecode.model.dto.ProductTO;
import br.com.avenuecode.rest.ProductController;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@Transactional
public class ProductsServiceApiApplicationTests {

	@Autowired
	private ProductController productController;

	@Test
	public void testAllHierarchical() {
		final ResponseEntity<List<ProductTO>> response = productController.getAllHierarchical();

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.OK)),
				hasProperty("body", notNullValue())));

		List<ProductTO> listProductTo = response.getBody();
		assertEquals(listProductTo.size(), 3);
		Map<Integer, ProductTO> mapProductToMock = getMockListProductTO();

		for (ProductTO to : listProductTo) {
			ProductTO mockData = mapProductToMock.get(to.getId());

			assertTrue(to.getId().equals(mockData.getId()));
			assertTrue(to.getDescription().equals(mockData.getDescription()));
			assertTrue(to.getName().equals(mockData.getName()));

		}

	}

	@Test
	public void testAll() {
		final ResponseEntity<List<ProductNoHierachicalTO>> response = productController.getAll();

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.OK)),
				hasProperty("body", notNullValue())));

		List<ProductNoHierachicalTO> listProductTo = response.getBody();
		assertEquals(listProductTo.size(), 3);
		Map<Integer, ProductNoHierachicalTO> mapProductToMock = getMockListProductNoHierachicalTO();

		for (ProductNoHierachicalTO to : listProductTo) {
			ProductNoHierachicalTO mockData = mapProductToMock.get(to.getId());

			assertTrue(to.getId().equals(mockData.getId()));
			assertTrue(to.getDescription().equals(mockData.getDescription()));
			assertTrue(to.getName().equals(mockData.getName()));
		}

	}

	@Test
	public void testByIdSucess() {
		final ResponseEntity<List<ProductTO>> response = productController.getById(1);

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.OK)),
				hasProperty("body", notNullValue())));

		List<ProductTO> listProductTo = response.getBody();
		assertEquals(listProductTo.size(), 1);
		Map<Integer, ProductTO> mapProductToMock = getMockListProductTO();

		for (ProductTO to : listProductTo) {
			ProductTO mockData = mapProductToMock.get(to.getId());

			assertTrue(to.getId().equals(mockData.getId()));
			assertTrue(to.getDescription().equals(mockData.getDescription()));
			assertTrue(to.getName().equals(mockData.getName()));
		}

	}

	@Test
	public void testByIdNotSucess() {
		final ResponseEntity<List<ProductTO>> response = productController.getById(-999);

		assertThat(response,
				allOf(hasProperty("statusCode", equalTo(HttpStatus.NO_CONTENT)), hasProperty("body", nullValue())));

	}

	@Test
	public void testProductsByParentIdSucess() {
		final ResponseEntity<List<ProductTO>> response = productController.getProductsByParentId(1);

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.OK)),
				hasProperty("body", notNullValue())));

		List<ProductTO> listProductTo = response.getBody();
		assertEquals(listProductTo.size(), 2);
		Map<Integer, ProductTO> mapProductToMock = getMockListProductTO();

		for (ProductTO to : listProductTo) {
			ProductTO mockData = mapProductToMock.get(to.getId());

			assertTrue(to.getId().equals(mockData.getId()));
			assertTrue(to.getDescription().equals(mockData.getDescription()));
			assertTrue(to.getName().equals(mockData.getName()));
		}

	}

	@Test
	public void testProductsByParentIdNotSucess() {
		final ResponseEntity<List<ProductTO>> response = productController.getProductsByParentId(-999);

		assertThat(response,
				allOf(hasProperty("statusCode", equalTo(HttpStatus.NO_CONTENT)), hasProperty("body", nullValue())));

	}

	@Test
	public void testSaveProductSucess() {

		ProductRequest productWrapper = new ProductRequest();
		productWrapper.setName("Play Station 4");
		productWrapper.setDescription("Video Game Sony");

		final ResponseEntity<Object> response = productController.save(productWrapper);

		Product product = (Product) response.getBody();

		assertTrue(product != null);
		assertTrue(product.getId() != null);

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.CREATED)),
				hasProperty("body", notNullValue())));

	}

	@Test
	public void testSaveProductNotSucess() {

		ProductRequest productWrapper = new ProductRequest();
		// productWrapper.setName("Play Station 4");
		productWrapper.setDescription("Video Game Sony");

		final ResponseEntity<Object> response = productController.save(productWrapper);

		ApiError error = (ApiError) response.getBody();

		assertTrue(error.getMessage().equals("The Product's name must be informated."));

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.BAD_REQUEST)),
				hasProperty("body", notNullValue())));

	}

	@Test
	public void testUpdateProductSucess() {

		ProductRequest productWrapper = new ProductRequest();
		productWrapper.setId(2);
		productWrapper.setName("Joy Stick Ninetendo Switch");
		productWrapper.setDescription("Joy Stick Nintendo last generation");

		final ResponseEntity<Object> response = productController.update(productWrapper);

		Product product = (Product) response.getBody();

		assertTrue(product != null);
		assertTrue(product.getId() != null);

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.OK)),
				hasProperty("body", notNullValue())));

	}

	@Test
	public void testUpdateProductNotSucess() {

		ProductRequest productWrapper = new ProductRequest();
		productWrapper.setId(-999);
		productWrapper.setName("Joy Stick Ninetendo Switch");
		productWrapper.setDescription("Joy Stick Nintendo last generation");

		final ResponseEntity<Object> response = productController.update(productWrapper);

		ApiError error = (ApiError) response.getBody();

		assertTrue(error.getMessage().equals("Product is not in data base yet."));

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.BAD_REQUEST)),
				hasProperty("body", notNullValue())));

	}

	@Test
	public void testDeleteProductSucess() {

		ProductRequest productWrapper = new ProductRequest();
		productWrapper.setId(1);
		final ResponseEntity<Object> response = productController.delete(productWrapper);

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.OK)),
				hasProperty("body", notNullValue())));

	}

	@Test
	public void testDeleteProductNotSucess() {

		ProductRequest productWrapper = new ProductRequest();
		productWrapper.setId(-1);
		final ResponseEntity<Object> response = productController.delete(productWrapper);

		ApiError error = (ApiError) response.getBody();

		assertTrue(error.getMessage().equals("Product is not in data base yet."));

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.BAD_REQUEST)),
				hasProperty("body", notNullValue())));
	}

	private Map<Integer, ProductTO> getMockListProductTO() {

		Map<Integer, ProductTO> mapProductTO = new HashMap<Integer, ProductTO>();

		int indexProduct = 0;
		int indexImages = 0;

		ProductTO product1 = getNewProduct(++indexProduct, "Ninetendo Switch", "Video Game");
		addImagesTOProduct(indexImages, ImageType.JPG, product1);
		mapProductTO.put(product1.getId(), product1);

		ProductTO productParent = getNewProduct(indexProduct, "Ninetendo Switch", "Video Game");
		addImagesTOProduct(indexImages, ImageType.JPG, product1);

		indexImages += 4;
		ProductTO product2 = getNewProduct(++indexProduct, "Controle Ninetendo Switch", "Controle de Video Game");
		addImagesTOProduct(indexImages, ImageType.TIFF, product2);
		mapProductTO.put(product2.getId(), product2);

		indexImages += 4;
		ProductTO product3 = getNewProduct(++indexProduct, "Jogo Mario Ninetendo Switch", "Jogo de Video Game");
		addImagesTOProduct(indexImages, ImageType.GIF, product3);
		mapProductTO.put(product3.getId(), product3);

		productParent.setChildren(Arrays.asList(product2, product3));

		product2.setParent(getParent(productParent));
		product3.setParent(getParent(productParent));

		return mapProductTO;
	}

	private Map<Integer, ProductNoHierachicalTO> getMockListProductNoHierachicalTO() {

		Map<Integer, ProductNoHierachicalTO> mapProductTO = new HashMap<Integer, ProductNoHierachicalTO>();

		int indexProduct = 0;

		ProductNoHierachicalTO product1 = getNewProductNoHierachicalTO(++indexProduct, "Ninetendo Switch",
				"Video Game");
		mapProductTO.put(product1.getId(), product1);

		ProductNoHierachicalTO product2 = getNewProductNoHierachicalTO(++indexProduct, "Controle Ninetendo Switch",
				"Controle de Video Game");
		mapProductTO.put(product2.getId(), product2);

		ProductNoHierachicalTO product3 = getNewProductNoHierachicalTO(++indexProduct, "Jogo Mario Ninetendo Switch",
				"Jogo de Video Game");
		mapProductTO.put(product3.getId(), product3);

		return mapProductTO;
	}

	private ProductParentTO getParent(ProductTO to) {
		ProductParentTO parentTO = new ProductParentTO();
		parentTO.setId(to.getId());
		parentTO.setDescription(to.getDescription());
		parentTO.setImages(to.getImages());
		parentTO.setChildren(to.getChildren());
		return parentTO;
	}

	private ProductTO getNewProduct(int id, String name, String description) {
		ProductTO product = new ProductTO();
		product.setId(id);
		product.setName(name);
		product.setDescription(description);

		return product;
	}

	private ProductNoHierachicalTO getNewProductNoHierachicalTO(int id, String name, String description) {
		ProductNoHierachicalTO product = new ProductNoHierachicalTO();
		product.setId(id);
		product.setName(name);
		product.setDescription(description);

		return product;
	}

	private void addImagesTOProduct(int index, ImageType type, ProductTO product) {
		List<ImageTO> listImage = new ArrayList<ImageTO>();

		index++;
		ImageTO image1 = new ImageTO();
		image1.setId(index);
		image1.setType(type);
		listImage.add(image1);

		index++;
		ImageTO image2 = new ImageTO();
		image2.setId(index);
		image2.setType(type);
		listImage.add(image2);

		index++;
		ImageTO image3 = new ImageTO();
		image3.setId(index);
		image3.setType(type);
		listImage.add(image3);

		index++;
		ImageTO image4 = new ImageTO();
		image4.setId(index);
		image4.setType(type);
		listImage.add(image4);

		product.setImages(listImage);
	}

}
