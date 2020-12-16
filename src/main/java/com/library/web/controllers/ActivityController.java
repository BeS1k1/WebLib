package com.library.web.controllers;

import com.library.web.models.Activity;
import com.library.web.models.Ticket;
import com.library.web.models.User;
import com.library.web.repo.ActivityRepository;
import com.library.web.repo.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TicketRepo ticketRepo;

    @GetMapping("/activity")
    public String getActivity(Model model) {
        Iterable<Activity> activity = activityRepository.findAll();
        model.addAttribute("activity", activity);
        return "activity";
    }

    @PostMapping("/activity")
    public String activityFilter(@RequestParam String filter, Model model) {
        List<Activity> activity = activityRepository.findByTitleIgnoreCaseContaining(filter);
        model.addAttribute("activity", activity);
        return "/activity";
    }

    @GetMapping("/activity/add")
    public String activityAdd(Model model) {
        return "activityAdd";
    }

    @PostMapping("/activity/add")
    public String activityPostAdd(@RequestParam String title, @RequestParam Date date, @RequestParam int seats, @AuthenticationPrincipal User user, Model model) {
        Activity activity = new Activity(title, date, seats, user);
        activityRepository.save(activity);
        return "redirect:/activity";
    }

    @GetMapping("/activity/{id}")
    public String getActivityDetails(@PathVariable(value = "id") long id, Model model) {
        if(!activityRepository.existsById(id)){
            return "redirect:/books";
        }
        Optional<Activity> activity = activityRepository.findById(id);
        ArrayList<Activity> res = new ArrayList<>();
        activity.ifPresent(res::add);
        model.addAttribute("activity", res);
        return "activityDetails";
    }

    @PostMapping("/activity/{id}")
    public String activityRegistration(@PathVariable(value = "id") long id, @AuthenticationPrincipal User user){
        if(!activityRepository.existsById(id)){
            return "redirect:/books";
        }
        Activity activity = activityRepository.findById(id).orElseThrow(IllegalStateException::new);
        int seats = activity.getSeats();
        activity.setSeats(seats-1);
        activityRepository.save(activity);

        Ticket ticket = new Ticket(activity, user);
        ticketRepo.save(ticket);

        return "redirect:/activity";
    }


}
