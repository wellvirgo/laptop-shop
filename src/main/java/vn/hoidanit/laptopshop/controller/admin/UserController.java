package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadFileService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    private final UserService userService;
    private final UploadFileService uploadFileService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadFileService uploadFileService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadFileService = uploadFileService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> userList = this.userService.getListUsers();
        model.addAttribute("userList", userList);
        return "admin/user/showTableUser";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUserUpdatePage(Model model, @PathVariable long id) {
        model.addAttribute("currentUser", this.userService.getUserById(id));
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@ModelAttribute(name = "currentUser") User currentUser,
            @RequestParam("avatarFile") MultipartFile file) {
        User userUpdate = this.userService.getUserById(currentUser.getId());

        if (!file.isEmpty()) {
            String avatar = this.uploadFileService.handleSaveUploadFile(file, "avatars");
            userUpdate.setAvatar(avatar);
        }
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
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@ModelAttribute User currentUser) {
        this.userService.deleteUser(currentUser.getId());
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(Model model, @ModelAttribute(name = "newUser") @Valid User newUser,
            BindingResult newUserBindingResult, @RequestParam("avatarFile") MultipartFile file) {
        String avatar = this.uploadFileService.handleSaveUploadFile(file, "avatars");
        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());
        Role role = this.userService.getRoleByName(newUser.getRole().getName());
        List<FieldError> errList = newUserBindingResult.getFieldErrors();
        for (FieldError err : errList) {
            System.out.println(err.getField() + " -- " + err.getDefaultMessage());
        }
        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        } else {
            // set avatar, hashPassword, role to save user into database
            newUser.setAvatar(avatar);
            newUser.setPassword(hashPassword);
            newUser.setRole(role);
            // save user
            this.userService.saveUser(newUser);
        }
        return "redirect:/admin/user";
    }
}
