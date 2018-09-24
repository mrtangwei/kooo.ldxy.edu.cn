package cn.edu.ldxy.wechat.repository;


import cn.edu.ldxy.commons.repository.MongodbBaseRepository;
import cn.edu.ldxy.wechat.domain.WeChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Kooo on 2017/1/9.
 */
public interface WeChatRepository extends MongodbBaseRepository<WeChat, String> {

     Page<WeChat> findAll(Pageable pageable);
}
