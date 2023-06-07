package com.example.vsharstuk.numberreducer.service;

import com.example.vsharstuk.numberreducer.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrinterService {

    private final static String SEPARATOR = ",";

    public String reducePages(String[] pages) {
        List<Integer> sortedPages = getUniqueSortedPagesList(pages);

        int start = sortedPages.get(0);
        int prev = start;
        List<String> ranges = new LinkedList<>();
        for (int i = 1 ; i < sortedPages.size(); i++) {
            int next = sortedPages.get(i);
            if (next > prev + 1) {
                ranges.add(convertRangeToString(start, prev));
                start = next;
            }
            prev = next;
        }
        ranges.add(convertRangeToString(start, prev));
        return String.join(SEPARATOR, ranges);
    }

    private String convertRangeToString(int start, int end) {
        if (start == end)  {
            return String.valueOf(start);
        }
        return String.valueOf(start) + '-' + end;
    }

    private List<Integer> getUniqueSortedPagesList(String[] pages) {
        return Arrays.stream(pages)
                .filter(StringUtils::isNotBlank)
                .map(this::convertToInteger)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    private int convertToInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Input is too long. Max number is " + Integer.MAX_VALUE);
        }
    }
}
