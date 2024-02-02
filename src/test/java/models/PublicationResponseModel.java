package models;

import lombok.Data;

@Data
public class PublicationResponseModel{
	private int id;
	private String title;
	private String body;
	private int userId;
}
