package br.com.avenuecode.business;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.avenuecode.exceptions.BusinessException;
import br.com.avenuecode.model.Image;
import br.com.avenuecode.model.Product;
import br.com.avenuecode.service.ImageService;
import br.com.avenuecode.service.ProductService;

@Service
public class ImageBO {

	// define the logger
	private static final Logger log = LogManager.getLogger(ImageBO.class);


	@Autowired
	private ImageService service;

	@Autowired
	private ProductService productService;

	public void save(Image image) throws BusinessException {

		log.info("ImageBO.save ");

		if (image == null) {
			log.info("Image null can not be created.");
			throw new BusinessException("Image null can not be created.");
		}
		if (image.getType() == null || image.getType().toString().isEmpty()) {
			log.info("Image type can not be null.");
			throw new BusinessException("Image type can not be null.");
		}

		try {
			service.save(image);
		} catch (DataIntegrityViolationException e) {
			log.info("You need a valid product to create a image.");
			throw new BusinessException("You need a valid product to create a image.");
		}
	}

	public void save(List<Image> images) throws BusinessException {
		for (Image image : images) {
			if (image == null) {
				throw new BusinessException("Image null can not be created.");
			}
		}
		service.save(images);
	}

	public void update(Image image) throws BusinessException {

		log.info("ImageBO.update ");

		if (image != null && image.getId() != null) {
			Image imageUpdate = service.findById(image.getId());
			if (imageUpdate != null && imageUpdate.getId() != null) {

				Product product = image.getProduct();

				if (product != null) {
					product = productService.findOne(image.getProduct().getId());

					if (product == null || product.getId() == null) {
						log.info("You need a valid product to update a image.");
						throw new BusinessException("You need a valid product to update a image.");
					}
				}


				try {
					service.updade(imageUpdate);
				} catch (DataIntegrityViolationException e) {
					log.info("You need a valid product to create a image.");
					throw new BusinessException("You need a valid product to create a image.");
				}

			} else {
				throw new BusinessException("Image is not in data base yet");
			}
		} else {
			throw new BusinessException("Image is not in data base yet");
		}

	}

	public void updade(List<Image> images) {
		service.save(images);
	}

	public void delete(Image image) throws BusinessException {
		log.info("ImageBO delete");
		if (image != null && image.getId() != null) {
			Image imageDelete = service.findById(image.getId());
			if (imageDelete != null && imageDelete.getId() != null) {
				service.delete(imageDelete);
			} else {
				log.info("Image is not in data base yet");
				throw new BusinessException("Image is not in data base yet");
			}
		} else {
			log.info("Image is not in data base yet");
			throw new BusinessException("Image is not in data base yet");
		}
	}

	public void delete(List<Image> images) {
		service.delete(images);
	}

	public List<Image> findAllByProduct(Integer id) {
		log.info("ImageBO findAllByProduct");
		return service.findAllByProduct(id);
	}

}
