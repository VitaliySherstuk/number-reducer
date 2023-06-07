package com.example.vsharstuk.numberreducer.service;

import com.example.vsharstuk.numberreducer.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PrinterServiceTest {

    private PrinterService printerService;

    @BeforeEach
    public void seUp() {
        printerService = new PrinterService();
    }

    @Test
    public void testReducePages() {
        //given
        String[] pages = {"1","2","4","6","25","9","26","27","28","5"};
        String expectedResult = "1-2,4-6,9,25-28";

        //when
        String result = printerService.reducePages(pages);

        //then
        assertEquals(expectedResult, result);
    }

    @Test void testReducePages_whenSingleElement() {
        //given
        String[] pages = {"1"};
        String expectedResult = "1";

        //when
        String result = printerService.reducePages(pages);

        //then
        assertEquals(expectedResult, result);
    }

    @Test
    public void testReducePages_whenElementIsNull() {
        //given
        String[] pages = {"1","2","4","6","25", null};
        String expectedResult = "1-2,4,6,25";

        //when
        String result = printerService.reducePages(pages);

        //then
        assertEquals(expectedResult, result);
    }

    @Test
    public void testReducePages_whenElementIsEmpty() {
        //given
        String[] pages = {"1","2"," ","6","25", "4"};
        String expectedResult = "1-2,4,6,25";

        //when
        String result = printerService.reducePages(pages);

        //then
        assertEquals(expectedResult, result);
    }

    @Test
    public void testReducePages_whenElementIsTooLong_thenThrowBadRequestException() {
        //given & when
        String[] pages = {"1","2","4","6","25", "2147483648"};

        Exception exception = assertThrows(BadRequestException.class, () -> {
            printerService.reducePages(pages);
        });

        String expectedMessage = "Input is too long. Max number is " + Integer.MAX_VALUE;
        String actualMessage = exception.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
    }
}