package com.example.vsharstuk.numberreducer.controller;

import com.example.vsharstuk.numberreducer.dto.PageNumberListDto;
import com.example.vsharstuk.numberreducer.service.PrinterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/printer")
@RequiredArgsConstructor
public class PrinterController {

    @Autowired
    private PrinterService printerService;

    @GetMapping("/reducedPageNumbers")
    public PageNumberListDto reducePages(@RequestParam("rawPageNumbers") String rawPageNumbers) {
        String result = printerService.reducePages(rawPageNumbers.trim().split(","));
        return null;
    }
}
