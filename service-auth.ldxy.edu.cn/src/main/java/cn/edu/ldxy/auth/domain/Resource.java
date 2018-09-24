package cn.edu.ldxy.auth.domain;


 import lombok.Getter;
import lombok.Setter;
 import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "resources")
public class Resource implements Serializable {

 
	/**
	 * 
	 */
	private static final long serialVersionUID = 5638569868586421671L;

	@Id
	private String id;

	private String name;

	private String memo;

	private String url;



}
