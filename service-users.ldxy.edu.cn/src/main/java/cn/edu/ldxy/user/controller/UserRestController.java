package cn.edu.ldxy.user.controller;


import cn.edu.ldxy.user.domain.User;
import cn.edu.ldxy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author Kooo
 *
 */
@RestController
public class UserRestController {



    @Autowired(required=true)
    private UserService userService;

    @GetMapping(value = "/feign-user-service/{username}")
    public User get(@PathVariable("username") String username){
        return this.userService.findByUsername(username);
    }

    @GetMapping(value="/feign-user-service/user/query/{username}/{password}")
    public  String queryUser(@PathVariable("username")String username, @PathVariable("password")String password)
    {
        User user = this.userService.findByUsername(username);
        if(user == null) {
            return "1";
        } else {
            if(!BCrypt.checkpw(password, user.getPassword())){
                return "2";
            }else{
                return "3";
            }
        }
    }

    /*
    public User get(@RequestHeader("username") String username, @RequestHeader("password") String password){
        return this.userService.findByUsername(username);
    }
*/


/*
    public String get(@RequestBody User user){
        return this.userService.findByUsername(user.getUsername()).toString();
    }*/

    @PostMapping(value = "/security/user/add/")
    public User save(@RequestBody User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return this.userService.save(user);
    }


    @GetMapping(value="/admin/user/all")
    public Page<User> User(Pageable pageable)
    {
        return this.userService.findAll(pageable);
    }


    @GetMapping(value="/security/admin/User/query/{username}")
    public Page<User> getUsers(@PathVariable("username")String username, Pageable pageable)
    {
        return this.userService.findByUsernameLike(username, pageable);
    }



/*    @GetMapping(value="/security/admin/user/role/{role}")
    public Page<User> getAdminContaining(@PathVariable("role")String role, Pageable pageable, Principal principal)
    {
        if(this.userService.findByUsername(principal.getName()).getRole().equalsIgnoreCase("ROLE_SUPER_ADMIN")){
            return this.userService.findByRoleContaining(role,pageable);
        }else {
            return null;
        }
    }*/


    /**
     * 用户修改密码，用户登录后自己修改自己密码，不是管理员修改用户密码
     * @param password
     * @return boolean
     */
    @PostMapping(value="/security/user/changePassword/{password}")
    public @ResponseBody
    boolean changePassword(
            @PathVariable("password")String password, Principal principal, HttpServletRequest request)
    {
        User u = this.userService.findByUsername(principal.getName());
        if (null != u) {
             return this.userService.changePassword(u, password);
        }else {
            return false;
        }

    }


    /**
     * 增加用户，并对密码进行加密
     * @param u
     * @param request
     * @param principal
     * @return
     */
    @PostMapping(value="/security/admin/user/add/")
    public boolean addUser(@RequestBody User u, HttpServletRequest request, Principal principal)
    {
        if(principal != null) {
            if (this.userService.findByUsername(u.getUsername()) != null) {
                u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
                this.userService.save(u);
                 return true;
            }
            return false;
        }else{
            return true;
        }
    }

    /**
     * 个人修改电话号码
     * @param u
     * @param request
     * @param principal
     * @return
     */
    @PostMapping(value="/security/user/update/tel/")
    public boolean updateTel(@RequestBody User u, HttpServletRequest request, Principal principal)
    {
        if(principal != null) {
           User User = this.userService.findByUsername(principal.getName());
           User.setTel(u.getTel());
           this.userService.save(User);
            return true;
        }else{
            return false;
        }
    }


    /**
     * 根据用户Id删除用户
     * @param request
     * @param principal
     * @return
     */
    @DeleteMapping(value="/security/admin/user/delete/")
    public String  deleteUser(@RequestBody User User, HttpServletRequest request, Principal principal)
    {
        if(principal != null) {
            if (this.userService.findById(User.getId()) != null) {
                 this.userService.deleteById(User.getId());
            }
            return "redirect:" + "/admin/User";
        }else{
            return "/login";
        }
    }

    /**
     * 管理员修改用户密码
     * @param u
     * @return
     */
    @PostMapping(value = "/security/admin/update/password/")
    public  String post(@RequestBody User u, HttpServletRequest request) {
        User User = this.userService.findByUsername(u.getUsername());
        if(User != null){
            User.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
            this.userService.save(User);
             return "success";
        }else {
            return "false";
        }
    }

    /**
     *  用户修改密码
     * @return
     */
    @PostMapping(value = "/security/user/update/password/{oldPassword}/{newPassword}/")
    public  boolean updatePassword(@PathVariable("oldPassword")String oldPassword,
                                   @PathVariable("newPassword")String newPassword,
                                   HttpServletRequest request, Principal principal) {
        try{
            User User = this.userService.findByUsername(principal.getName());
            if(BCrypt.checkpw(oldPassword,User.getPassword()))
            {
                User.setPassword(new BCryptPasswordEncoder().encode(newPassword));
                this.userService.save(User);
                 return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }



    @RequestMapping(value = "/login/success", method = RequestMethod.GET)
    public @ResponseBody
    User login(Principal principal, HttpServletRequest request) {

        if (principal == null) {
            return null;
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);
       // user.setPassword("no password");

         return user;
    }
}
