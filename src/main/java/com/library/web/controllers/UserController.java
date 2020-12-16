package com.library.web.controllers;

import com.library.web.models.*;
import com.library.web.repo.ActivityRepository;
import com.library.web.repo.TicketRepo;
import com.library.web.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private ActivityRepository activityRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}")
    public String userSave(
            @PathVariable(value = "userId") long id,
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            Model model){
        User user = userRepository.findById(id).orElseThrow(IllegalStateException::new);
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if ( roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal User user, Model model){
        List<Ticket> tickets = ticketRepo.findByUser_id(user.getId());
        List<Activity> activities = new ArrayList<>();
        for(Ticket ticket : tickets){
            Activity activity = ticket.getActivity();
            activities.add(activity);
        }
        model.addAttribute("activity", activities);
        model.addAttribute("username", user.getUsername());

        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(@AuthenticationPrincipal User user, Model model){

        model.addAttribute("username", user.getUsername());

        return "profileEdit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@AuthenticationPrincipal User user, @RequestParam String password){
        if (!StringUtils.isEmpty(password))
            user.setPassword(password);

        userRepository.save(user);
        return "redirect:/user/profile";
    }

}
