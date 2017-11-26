package br.com.avenuecode.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@NamedEntityGraph(name = "Product.detail", attributeNodes = @NamedAttributeNode("images"))
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6131311050358241535L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Transient
	private Integer productParentId;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "parent_id")
	private Product parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true)
	@Cascade({ CascadeType.REMOVE })
	private Set<Product> children = new LinkedHashSet<Product>();

	@Cascade({ CascadeType.REMOVE })
	@OrderBy("id asc")
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Image> images = new LinkedHashSet<Image>();

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<Product> getChildren() {

		if (children != null) {
			children.clear();
		}

		return children;
	}

	public void setChildren(Set<Product> children) {
		this.children = children;
	}

	public Product() {
	}

	public Product(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Product(String name) {
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

	public Product getParent() {
		return parent;
	}

	public void setParent(Product parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getProductParentId() {
		return productParentId;
	}

	public void setProductParentId(Integer productParentId) {
		this.productParentId = productParentId;
	}

}
