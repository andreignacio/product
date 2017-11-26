package br.com.avenuecode.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductTO implements Serializable {

	private static final long serialVersionUID = -6131311050358241535L;

	private Integer id;

	private String name;

	private String description;

	private List<ImageTO> images;

	private ProductTO parent;

	private List<ProductTO> children = new ArrayList<ProductTO>();

	public ProductTO() {
	}

	public ProductTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public ProductTO(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ImageTO> getImages() {
		return images;
	}

	public void setImages(List<ImageTO> images) {
		this.images = images;
	}

	public ProductTO getParent() {
		return parent;
	}

	public void setParent(ProductTO parent) {
		this.parent = parent;
	}

	public List<ProductTO> getChildren() {
		return children;
	}

	public void setChildren(List<ProductTO> children) {
		this.children = children;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (children == null ? 0 : children.hashCode());
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (images == null ? 0 : images.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (parent == null ? 0 : parent.hashCode());
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
		ProductTO other = (ProductTO) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (images == null) {
			if (other.images != null)
				return false;
		} else if (!images.equals(other.images))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (other.parent == null) {
			if (parent != null)
				return false;
		} else if (parent.getId() != null) {
			if (other.parent.getId() == null)
				return false;
		} else if (other.parent.getId() != null) {
			if (parent.getId() == null)
				return false;
		} else if (other.parent.getId() != null && parent.getId() != null) {
			if (!parent.getId().equals(other.parent.getId()))
				return false;
		}

		return true;
	}

}
