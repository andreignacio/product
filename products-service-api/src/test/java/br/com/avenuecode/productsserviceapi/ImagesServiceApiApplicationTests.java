package br.com.avenuecode.productsserviceapi;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import br.com.avenuecode.model.Image;
import br.com.avenuecode.model.dto.ImageTO;
import br.com.avenuecode.model.dto.ImageUpdateTO;
import br.com.avenuecode.model.dto.ProductNoHierachicalTO;
import br.com.avenuecode.rest.ImageController;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@Transactional
public class ImagesServiceApiApplicationTests {

	@Autowired
	private ImageController imageController;

	@Test
	public void testAllByProduct() {
		final ResponseEntity<List<ImageTO>> response = imageController.getAllByProduct(1);

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.OK)),
				hasProperty("body", notNullValue())));

		List<ImageTO> listImageTo = response.getBody();
		assertEquals(listImageTo.size(), 4);

	}


	@Test
	public void testSaveImageSucess() {

		ImageTO to = new ImageTO();
		ProductNoHierachicalTO productTO = new ProductNoHierachicalTO();
		productTO.setId(1);
		to.setProduct(productTO);
		to.setType(ImageType.GIF);

		final ResponseEntity<Object> response = imageController.save(to);

		Image image = (Image) response.getBody();

		assertTrue(image != null);
		assertTrue(image.getId() != null);

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.CREATED)),
				hasProperty("body", notNullValue())));

	}



	@Test
	public void testUpdateImagetSucess() {

		ImageUpdateTO to = new ImageUpdateTO();
		to.setId(1);
		to.setType(ImageType.JPG);

		final ResponseEntity<Object> response = imageController.update(to);

		Image image = (Image) response.getBody();

		assertTrue(image != null);
		assertTrue(image.getId() != null);

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.OK)),
				hasProperty("body", notNullValue())));

	}



	@Test
	public void testDeleteImageSucess() {


		ImageUpdateTO to = new ImageUpdateTO();
		to.setId(1);
		to.setType(ImageType.JPG);

		final ResponseEntity<Object> response = imageController.delete(to);

		assertThat(response, allOf(notNullValue(), hasProperty("statusCode", equalTo(HttpStatus.OK)),
				hasProperty("body", notNullValue())));

	}





}
