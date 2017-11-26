package br.com.avenuecode.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ImageUpdateTO extends ImageTO implements Serializable {

	private static final long serialVersionUID = 2128787860415180858L;

	public ImageUpdateTO() {
		
	}
	


	@JsonIgnore
	private ProductNoHierachicalTO product;




}
