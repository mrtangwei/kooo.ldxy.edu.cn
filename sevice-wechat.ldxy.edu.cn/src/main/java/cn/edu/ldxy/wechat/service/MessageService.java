/**
 * 
 */
package cn.edu.ldxy.wechat.service;


 import cn.edu.ldxy.commons.service.MongodbBaseService;
 import cn.edu.ldxy.wechat.domain.Message;
 import cn.edu.ldxy.wechat.repository.MessageRepository;
 import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author jee
 * 
 */
public interface MessageService extends MongodbBaseService<Message, String, MessageRepository> {


   Page<Message> findByTitleLike(String title, Pageable pageable);

}
