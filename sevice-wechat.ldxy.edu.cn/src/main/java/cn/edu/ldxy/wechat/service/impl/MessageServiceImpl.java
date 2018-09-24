/**
 * 
 */
package cn.edu.ldxy.wechat.service.impl;


import cn.edu.ldxy.commons.service.MongodbBaseService;
import cn.edu.ldxy.commons.service.impl.MongodbBaseServiceImpl;
import cn.edu.ldxy.wechat.domain.Message;
import cn.edu.ldxy.wechat.repository.MessageRepository;
import cn.edu.ldxy.wechat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author jee
 * 
 */
@Service
public class MessageServiceImpl extends MongodbBaseServiceImpl<Message, String, MessageRepository>
		implements MessageService {


	@Autowired
	private MessageRepository messageRepository;

	public Page<Message> findByTitleLike(String title, Pageable pageable){
		return this.messageRepository.findByTitleLike(title,pageable);
	}

}
