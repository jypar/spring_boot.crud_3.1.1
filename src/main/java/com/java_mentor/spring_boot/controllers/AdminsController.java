package com.java_mentor.spring_boot.controllers;

import com.java_mentor.spring_boot.entities.User;
import com.java_mentor.spring_boot.services.RoleService;
import com.java_mentor.spring_boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("allRoles", roleService.getRolesList());
        model.addAttribute("user", userService.findById(id));
        return "update";
    }

    @PostMapping("/adduser")
    public String addUser(@Validated(User.class) @ModelAttribute("user") User user,
                          BindingResult result) {
        if(result.hasErrors()) {
            return "error";
        }
        userService.addOrUpdate(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute(new User());
        model.addAttribute("allRoles", roleService.getRolesList());
        return "create";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.remove(id);
        return "redirect:/admin";
    }
}
