package cn.edu.ldxy.auth.domain;


import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "roleResources")
public class RoleResources  implements Serializable {

 
	/**
	 * 
	 */
	private static final long serialVersionUID = -6418512268027045996L;

	@Id
	private String id;

	private String role;

	private String roleName;

	private JSONObject urlJson = new JSONObject();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public JSONObject getUrlJson() {
		return urlJson;
	}

	public void setUrlJson(JSONObject urlJson) {
		this.urlJson = urlJson;
	}


}
