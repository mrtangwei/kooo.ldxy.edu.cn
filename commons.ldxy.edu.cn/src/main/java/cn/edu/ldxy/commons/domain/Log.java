/**
 * 日志统计对象
 */
package cn.edu.ldxy.commons.domain;


 import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author  Kooo
 * 
 */

 @Setter
 @Getter
@Document(collection = "logs")
public class Log extends IdEntity {

 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8681914465470191080L;

	@Id
	private String id;

	/**
	 * 操作
	 */
	private String op;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date time;

	private String exe;

	private String ip;


}
