package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/create")
    public String viewCreateAccident(Model model) {
        List<AccidentType> accidentTypes = accidentTypeService.findAll();
        model.addAttribute("types", accidentTypes);
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "accident/createAccident";
    }

    @PostMapping("/save")
    public String saveAccident(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidentService.save(accident, ids);        return "redirect:/index";
    }

    @GetMapping("/edit")
    public String editAccident(Model model, @RequestParam int id) {
        var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Accident not found");
            return "error/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "accident/editAccident";
    }

    @PostMapping("/update")
    public String updateAccident(Model model, @ModelAttribute Accident accident, HttpServletRequest req) {
        String[] rIds = req.getParameterValues("rIds");
        var result = accidentService.update(accident, rIds);
        if (result == null) {
            model.addAttribute("message", "Accident not updated");
            return "error/404";
        }
        model.addAttribute("accidents", accidentService.findAll());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "index/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccident(Model model, @PathVariable int id) {
        accidentService.deleteById(id);
        model.addAttribute("accidents", accidentService.findAll());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "index/index";
    }

}
