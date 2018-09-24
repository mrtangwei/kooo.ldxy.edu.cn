/**
 * 
 */
package cn.edu.ldxy.wechat.service;


import cn.edu.ldxy.commons.service.MongodbBaseService;
import cn.edu.ldxy.wechat.domain.WeChat;
import cn.edu.ldxy.wechat.repository.WeChatRepository;

/**
 * @author jee
 * 
 */
public interface WeChatService extends MongodbBaseService<WeChat, String, WeChatRepository> {

  String getAccessToken();
}
