package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Role;
import web.model.User;
import web.repo.UserRepo;
//import web.service.UserService;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
public class AdminController extends HttpServlet {

    @Autowired
    private UserRepo userRepo;

    @RequestMapping("/admin") //url показа usera  в приложении(может не совпадать с url запуска сервера)
    public String getIndex(Model model) {
        Iterable<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "showUsers";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public String getPage() {
        return "addUser";
    }


    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user, @RequestParam(value = "role_id") Set<Role> role) {
        user.setRoles(role) ;
        userRepo.save(user);
        return "redirect:/admin";//todo   привести  к такому виду!!!/
    }
    @RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
    public String getDeletePage(@RequestParam(value="deleteId") Integer id, Model model) {
        Optional<User> user = userRepo.findById(id);
        model.addAttribute("user", user);
        return "deleteUser";
    }


    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
    public String getDeleteUser(@RequestParam(value="deleteId") Integer id) {
        userRepo.deleteById(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.GET)
    public String getPage(@RequestParam(value="updataId") Integer id, Model model) {
        User user = userRepo.findUserById(id);
        model.addAttribute("user", user);
        return "updateUser";
    }


    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String getUpdateUser(@ModelAttribute User user, @RequestParam Set<Role> role) {

        user.setRoles(role);
        User userUpdate = userRepo.findUserById(user.getId());
        userUpdate.setName(user.getUsername());
        userUpdate.setPassword(user.getPassword());
        userUpdate.setMoney(user.getMoney());
        userUpdate.setRoles((Set<Role>) user.getAuthorities());
        userRepo.save(userUpdate);
        //service.updateUser(userUpdate);
        return "redirect:/admin";
    }


}

