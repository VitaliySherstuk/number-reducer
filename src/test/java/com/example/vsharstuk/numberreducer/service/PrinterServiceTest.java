package com.example.vsharstuk.numberreducer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PrinterServiceTest {

    private PrinterService printerService;

    @BeforeEach
    public void seUp() {
        printerService = new PrinterService();
    }

    @Test
    public void reducePages() {
        //given
        String[] pages = {"1","2","4","6","25","9","26","27","28","5"};
        String expectedResult = "1-2,4-6,9,25-28";

        //when
        String result = printerService.reducePages(pages);

        //then
        assertEquals(expectedResult, result);
    }

    @Test void reducePages_whenSingleElement() {
        //given
        String[] pages = {"1"};
        String expectedResult = "1";

        //when
        String result = printerService.reducePages(pages);

        //then
        assertEquals(expectedResult, result);
    }

    @Test
    public void reducePages_whenElementIsNull() {
        //given
        String[] pages = {"1","2","4","6","25", null};
        String expectedResult = "1-2,4,6,25";

        //when
        String result = printerService.reducePages(pages);

        //then
        assertEquals(expectedResult, result);
    }

    @Test
    public void reducePages_whenElementIsEmpty() {
        //given
        String[] pages = {"1","2"," ","6","25", "4"};
        String expectedResult = "1-2,4,6,25";

        //when
        String result = printerService.reducePages(pages);

        //then
        assertEquals(expectedResult, result);
    }
}