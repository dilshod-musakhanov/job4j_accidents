package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeMemService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeMemService accidentTypeMemService;

    @GetMapping("/create")
    public String viewCreateAccident(Model model) {
        List<AccidentType> accidentTypes = accidentTypeMemService.getAllAccidentType();
        model.addAttribute("accidentTypes", accidentTypes);
        return "accident/createAccident";
    }

    @PostMapping("/save")
    public String saveAccident(@ModelAttribute Accident accident, @RequestParam int typeId) {
        var accidentType = accidentTypeMemService.getAccidentTypeById(typeId).get();
        accident.setType(accidentType);
        accidentService.addAccident(accident);
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
        model.addAttribute("accidentTypes", accidentTypeMemService.getAllAccidentType());
        return "accident/editAccident";
    }

    @PostMapping("/update")
    public String updateAccident(Model model, @ModelAttribute Accident accident, @RequestParam int id, @RequestParam int typeId) {
        var type = accidentTypeMemService.getAccidentTypeById(typeId).get();
        accident.setType(type);
        var result = accidentService.updateAccident(id, accident);
        if (!result) {
            model.addAttribute("message", "Accident not updated");
            return "error/404";
        }
        model.addAttribute("accidents", accidentService.getAllAccidents());
        return "index/index";
    }
}
