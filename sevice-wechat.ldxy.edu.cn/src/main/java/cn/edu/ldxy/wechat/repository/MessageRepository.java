package cn.edu.ldxy.wechat.repository;



import cn.edu.ldxy.commons.repository.MongodbBaseRepository;
import cn.edu.ldxy.wechat.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Kooo on 2017/1/9.
 */
public interface MessageRepository extends MongodbBaseRepository<Message, String> {

     Page<Message> findAll(Pageable pageable);

     Page<Message> findByTitleLike(String title, Pageable pageable);
}
