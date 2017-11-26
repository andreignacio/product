package br.com.avenuecode.model.dto;

import java.io.Serializable;

import br.com.avenuecode.constants.ImageType;


public class ImageTO implements Serializable {

	private static final long serialVersionUID = 2128787860415180858L;

	public ImageTO() {
		
	}
	
	private Integer id;

	private ImageType type;

	private ProductNoHierachicalTO product;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ImageType getType() {
		return type;
	}

	public void setType(ImageType type) {
		this.type = type;
	}

	

	public ImageTO(ImageType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageTO other = (ImageTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public ProductNoHierachicalTO getProduct() {
		return product;
	}

	public void setProduct(ProductNoHierachicalTO product) {
		this.product = product;
	}

}
