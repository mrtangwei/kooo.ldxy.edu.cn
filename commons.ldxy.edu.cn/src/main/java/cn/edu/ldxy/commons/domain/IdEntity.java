/**
 * IdEntity.java
 * --------------------------------------
 * CREATED ON 2012-08-09  
 * --------------------------------------
 */
package cn.edu.ldxy.commons.domain;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * I alone sit lingering here <br>
 * <b>Writer</b>: <a href="mailto:Kooogwei@vip.qq.com">amous</a><br>
 * <b>CreateTime<b>: 2012-08-09 下午11:30:20
 */
@SuppressWarnings("serial")
public abstract class IdEntity   implements Serializable {
    
    public IdEntity() {
        super();
    }
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("X-Real-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
