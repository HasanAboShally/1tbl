package com.onetbl.web.beans;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
@JsonAutoDetect
public class Project {

	 @JsonProperty
	private int id;
	 @JsonProperty
	private int ownerId;
	 @JsonProperty
	private String name;
	 @JsonProperty
	private String token;
	 @JsonProperty
	private String description;
	 @JsonProperty
	private String creationDade;
	 @JsonProperty
	private String lastModified;
	 @JsonProperty
	private String iconUrl;
	
	public Project() {}
	
	public Project(int id, String name, String iconUrl) {
		this.name = name;
		this.id = id;
		this.setIconUrl(iconUrl);
	}

	
	public Project(int id, int ownerId, String name, String token,
			String description, String creationDade, String lastModified,
			String iconUrl) {
		this.id = id;
		this.ownerId = ownerId;
		this.name = name;
		this.token = token;
		this.description = description;
		this.creationDade = creationDade;
		this.lastModified = lastModified;
		this.iconUrl = iconUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getCreationDate() {
		return creationDade;
	}

	public void setCreationDate(String creationDatde) {
		this.creationDade = creationDatde;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public void setIconUrl(String imgPath) {
		this.iconUrl = imgPath;
	}

	
}
