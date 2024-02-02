package models;

import lombok.Data;

@Data
public class CreatePublicationResponseModel {
	private int id;
	private String title;
	private String body;
	private int userId;
}
