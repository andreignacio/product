package br.com.avenuecode.rest;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import br.com.avenuecode.business.ImageBO;
import br.com.avenuecode.error.ApiError;
import br.com.avenuecode.exceptions.BusinessException;
import br.com.avenuecode.model.Image;
import br.com.avenuecode.model.dto.ImageTO;
import br.com.avenuecode.model.dto.ImageUpdateTO;
import br.com.avenuecode.service.ImageService;

@RestController
@RequestMapping(value = "/image")
public class ImageController {

	@Autowired
	private ImageBO imageBO;

	@Autowired
	ImageService imageService;

	private ModelMapper modelMapper = new ModelMapper();

	@GetMapping(value = "/product/{productId}")
	public ResponseEntity<List<ImageTO>> getAllByProduct(@PathVariable("productId") Integer id) {

		List<Image> images = imageBO.findAllByProduct(id);
		List<ImageTO> imagesTO = new ArrayList<>();

		if (images == null || images.isEmpty()) {
			return new ResponseEntity<List<ImageTO>>(imagesTO, HttpStatus.NO_CONTENT);
		}

		imagesTO = toImageTOList(images);

		ResponseEntity<List<ImageTO>> responseEntity = new ResponseEntity<>(imagesTO, HttpStatus.OK);
		return responseEntity;
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody ImageTO imageTO) {
		Image image = null;
		try {
			image = modelMapper.map(imageTO, Image.class);
			imageBO.save(image);
		} catch (BusinessException e) {
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "error occurred");
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}

		ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(image, HttpStatus.CREATED);
		return responseEntity;
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestBody ImageUpdateTO imageTO) {
		Image image = null;
		try {
			image = modelMapper.map(imageTO, Image.class);
			imageBO.update(image);
		} catch (BusinessException e) {
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "error occurred");
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}

		ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(image, HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestBody ImageUpdateTO imageTO) {
		Image image = null;
		try {
			image = modelMapper.map(imageTO, Image.class);
			imageBO.delete(image);
		} catch (BusinessException e) {
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "error occurred");
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}

		ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(image, HttpStatus.OK);
		return responseEntity;
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	private List<ImageTO> toImageTOList(List<Image> images) {
		List<ImageTO> imageTO = new ArrayList<ImageTO>();
		for (Image image : images) {
			ImageTO to = modelMapper.map(image, ImageTO.class);
			imageTO.add(to);
		}
		return imageTO;
	}

}
