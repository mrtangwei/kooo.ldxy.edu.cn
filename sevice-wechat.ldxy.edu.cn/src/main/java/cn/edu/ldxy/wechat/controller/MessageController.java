package cn.edu.ldxy.wechat.controller;

import cn.edu.ldxy.wechat.domain.Message;
import cn.edu.ldxy.wechat.domain.User;
import cn.edu.ldxy.wechat.service.MessageService;
import cn.edu.ldxy.wechat.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.text.SimpleDateFormat;


/**
 * Created by Kooo on 2017/11/10.
 */
@RestController(value = "RestMessageController")
public class MessageController{

    @Autowired
    private WeChatService weChatService;


    @Autowired
    private MessageService messageService;




    @PostMapping(value = "/feign-user-service/wechat/message")
    public boolean add(@RequestBody Message message, Principal principal,  HttpServletRequest request){
        try{

            this.sendMsg(message);
            this.messageService.save(message);

            return true;
        }catch (Exception e){
            return false;
        }
    }



    @GetMapping(value = "/security/weChat/message/all")
    public Page<Message> all(Pageable pageable){
        return this.messageService.findAll(pageable);
    }


    private boolean sendMsg(Message message){
        String action = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
                + this.weChatService.getAccessToken();

        System.out.println(action);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            for (User u : message.getUserList()) {
                String msg = "  {\n" +
                        "           \"touser\":\""+ u.getWeChatId() + "\",\n" +
                        "           \"template_id\":\"k70WXtxu0uGOcc3J5IvWayAdg48U1oMCcO0jdmkpn5M\",\n" +
                       // "           \"url\":\"http://weixin.qq.com/download\",\n" +
                        "           \"data\":{\n" +
                        "                   \"first\": {\n" +
                        "                       \"value\":\"" + message.getTitle() + "\",\n" +
                        "                       \"color\":\"#173177\"\n" +
                        "                   },\n" +
                        "                   \"keyword1\":{\n" +
                        "                       \"value\":\"陇东学院 "+ message.getSender() +"\",\n" +
                        "                       \"color\":\"#173177\"\n" +
                        "                   },\n" +
                        "                   \"keyword2\": {\n" +
                        "                       \"value\":\""+ u.getUsername() + "\",\n"  +
                        "                       \"color\":\"#173177\"\n" +
                        "                   },\n" +
                        "                   \"keyword3\": {\n" +
                        "                       \"value\":\""+ format.format(System.currentTimeMillis()) +"\",\n" +
                        "                       \"color\":\"#173177\"\n" +
                        "                   },\n" +
                        "                   \"remark\":{\n" +
                        "                       \"value\":\"" + message.getContent()  + "\",\n" +
                        "                       \"color\":\"#173177\"\n" +
                        "                   }\n" +
                        "           }\n" +
                        "       }";
                //System.out.println(msg);
                URL url = new URL(action);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST");
                http.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                http.setDoOutput(true);
                http.setDoInput(true);
                System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
                System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

                http.connect();
                OutputStream os = http.getOutputStream();
                os.write(msg.getBytes("UTF-8"));// 传入参数
                os.flush();
                os.close();

            //this.restTemplate.

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);

            //System.out.println(new String(jsonBytes, "UTF-8"));


            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }


}

