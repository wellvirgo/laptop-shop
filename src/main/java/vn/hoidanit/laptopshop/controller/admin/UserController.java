package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadFileService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    private final UserService userService;
    private final UploadFileService uploadFileService;

    public UserController(UserService userService, UploadFileService uploadFileService) {
        this.userService = userService;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> userList = this.userService.getListUsers();
        model.addAttribute("userList", userList);
        return "/admin/user/showTableUser";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "/admin/user/detail";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUserUpdatePage(Model model, @PathVariable long id) {
        model.addAttribute("currentUser", this.userService.getUserById(id));
        return "/admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@ModelAttribute User currentUser) {
        User userUpdate = this.userService.getUserById(currentUser.getId());
        if (currentUser != null) {
            userUpdate.setFullName(currentUser.getFullName());
            userUpdate.setAddress((currentUser.getAddress()));
            userUpdate.setPhone(currentUser.getPhone());
            this.userService.saveUser(userUpdate);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id, @ModelAttribute User currentUser) {
        currentUser = this.userService.getUserById(id);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("id", id);
        return "/admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@ModelAttribute User currentUser) {
        this.userService.deleteUser(currentUser.getId());
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(Model model, @ModelAttribute User newUser,
            @RequestParam("avatarFile") MultipartFile file) {
        this.uploadFileService.handleSaveUploadFile(file, "avatars");
        // this.userService.saveUser(newUser);
        return "redirect:/admin/user";
    }
}
