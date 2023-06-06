package com.example.vsharstuk.numberreducer.controller;

import com.example.vsharstuk.numberreducer.dto.ErrorResponse;
import com.example.vsharstuk.numberreducer.dto.PageNumberListDto;
import com.example.vsharstuk.numberreducer.service.PrinterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class PrinterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrinterService printerService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void requestReducePages_correctInputData_returnStatusOk() throws Exception {
        //given
        String pages = "1,20,300,4";
        PageNumberListDto expectedResult = new PageNumberListDto(pages, pages);

        when(printerService.reducePages(pages.trim().split(","))).thenReturn(pages);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/printer/reducedPageNumbers")
                        .param("rawPageNumbers", pages))
                        .andExpect(status().isOk()).andReturn().getResponse();

        PageNumberListDto result = objectMapper.readValue(response.getContentAsString(), PageNumberListDto.class);

        //then
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "a,1,2,3", "1,2,a,4", "1,3,a", "1,-2,3"})
    public void requestReducePages_incorrectInputData_returnBadRequest(String pages) throws Exception {

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/printer/reducedPageNumbers")
                        .param("rawPageNumbers", pages))
                .andExpect(status().isOk()).andReturn().getResponse();

        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getStatus());
    }

    @Test
    public void requestReducePages_returnInternalError() throws Exception {
        //given
        String pages = "1,20,300,4";

        when(printerService.reducePages(pages.split(","))).thenThrow(new IllegalArgumentException());

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/printer/reducedPageNumbers")
                        .param("rawPageNumbers", pages))
                .andExpect(status().isInternalServerError()).andReturn().getResponse();

        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);

        //then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse.getStatus());
    }
}