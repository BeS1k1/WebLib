package com.library.web.controllers;

import com.library.web.models.Activity;
import com.library.web.repo.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping("/activity")
    public String getActivity(Model model){
        Iterable<Activity> activity = activityRepository.findAll();
        model.addAttribute("activity", activity);
        return "activity";
    }

    @PostMapping("/activity")
    public String activityFilter(@RequestParam String filter, Model model){
        List<Activity> activity = activityRepository.findByTitle(filter);
        model.addAttribute("activity", activity);
        return "/activity";
    }


}
