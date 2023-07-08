package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/create")
    public String viewCreateAccident() {
        return "accident/createAccident";
    }

    @PostMapping("/save")
    public String saveAccident(@ModelAttribute Accident accident) {
        accidentService.addAccident(accident);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String editAccident(Model model, @PathVariable int id) {
        var accidentOptional = accidentService.getAccidentById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Accident not found");
            return "error/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "accident/editAccident";
    }

    @PostMapping("/update")
    public String updateAccident(Model model, @ModelAttribute Accident accident, @RequestParam int id) {
        var result = accidentService.updateAccident(id, accident);
        if (!result) {
            model.addAttribute("message", "Accident not updated");
            return "error/404";
        }
        model.addAttribute("accidents", accidentService.getAllAccidents());
        return "index/index";
    }
}
