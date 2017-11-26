package br.com.avenuecode.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avenuecode.dao.ImageRepository;
import br.com.avenuecode.model.Image;

@Service
public class ImageService {
	
	@Autowired
	private ImageRepository repository;

	public void save(Image image) {
		repository.save(image);
	}

	public void save(List<Image> images) {
		repository.save(images);
	}

	public void updade(Image image) {
		repository.save(image);
	}

	public void updade(List<Image> images) {
		repository.save(images);
	}

	public void delete(Image image) {
		repository.delete(image);
	}

	public void delete(List<Image> images) {
		repository.delete(images);
	}

	public List<Image> findAllByProduct(Integer id) {
		return repository.findAllByProduct(id);
	}

	public Image findById(Integer id) {
		return repository.findOne(id);
	}

}
