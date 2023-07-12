package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeMemService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeMemService accidentTypeMemService;
    private final RuleService ruleService;

    @GetMapping("/create")
    public String viewCreateAccident(Model model) {
        List<AccidentType> accidentTypes = accidentTypeMemService.findAll();
        model.addAttribute("types", accidentTypes);
        model.addAttribute("rules", ruleService.findAll());
        return "accident/createAccident";
    }

    @PostMapping("/save")
    public String saveAccident(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidentService.addAccident(accident, ids);
        return "redirect:/index";
    }

    @GetMapping("/edit")
    public String editAccident(Model model, @RequestParam int id) {
        var accidentOptional = accidentService.getAccidentById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Accident not found");
            return "error/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        model.addAttribute("types", accidentTypeMemService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "accident/editAccident";
    }

    @PostMapping("/update")
    public String updateAccident(Model model, @ModelAttribute Accident accident, @RequestParam int id, HttpServletRequest req) {
        String[] rIds = req.getParameterValues("rIds");
        var result = accidentService.updateAccident(id, accident, rIds);
        if (!result) {
            model.addAttribute("message", "Accident not updated");
            return "error/404";
        }
        model.addAttribute("accidents", accidentService.findAll());
        return "index/index";
    }
}
