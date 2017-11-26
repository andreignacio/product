package br.com.avenuecode.loaddata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.avenuecode.constants.ImageType;
import br.com.avenuecode.model.Image;
import br.com.avenuecode.model.Product;
import br.com.avenuecode.service.ImageService;
import br.com.avenuecode.service.ProductService;

@Component
public class DataLoader implements CommandLineRunner {

	private ProductService productService;

	private ImageService imageService;

	public DataLoader(ProductService productService, ImageService imageService) {

		this.imageService = imageService;
		this.productService = productService;
	}

	@Override
	public void run(String... arg0) throws Exception {
		contextLoads();

	}

	private void contextLoads() {

		Product product1 = getNewProduct("Ninetendo Switch", "Video Game");
		productService.save(product1);
		addImagesTOProduct(ImageType.JPG, product1);

		Product product2 = getNewProduct("Controle Ninetendo Switch", "Controle de Video Game");
		product2.setParent(product1);
		productService.save(product2);
		addImagesTOProduct(ImageType.TIFF, product2);

		Product product3 = getNewProduct("Jogo Mario Ninetendo Switch", "Jogo de Video Game");
		product3.setParent(product1);
		productService.save(product3);
		addImagesTOProduct(ImageType.GIF, product3);

	}

	private Product getNewProduct(String name, String description) {
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);

		return product;
	}

	private void addImagesTOProduct(ImageType type, Product product) {
		Image image1 = new Image();
		image1.setType(type);
		image1.setProduct(product);
		Image image2 = new Image();
		image2.setType(type);
		image2.setProduct(product);
		Image image3 = new Image();
		image3.setType(type);
		image3.setProduct(product);
		Image image4 = new Image();
		image4.setProduct(product);
		image4.setType(type);

		imageService.save(image1);
		imageService.save(image2);
		imageService.save(image3);
		imageService.save(image4);

	}

}
