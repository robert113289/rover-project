package com.rover;

import com.rover.model.ReviewCsvRow;

import java.util.Arrays;
import java.util.List;

public class TestData {
    public static List<ReviewCsvRow> csvRows() {
        return Arrays.asList(
                new ReviewCsvRow(5,
                        "sitterImage1",
                        "endDate1",
                        "text1",
                        "ownerImage1",
                        "dogs1",
                        "sitter1",
                        "owner1",
                        "startDate1",
                        "sitterPhoneNumber1",
                        "sitterEmail1",
                        "ownerPhoneNumber1",
                        "ownerEmail1",
                        10),
                new ReviewCsvRow(4,
                        "sitterImage2",
                        "endDate2",
                        "text2",
                        "ownerImage2",
                        "dogs2",
                        "sitter2",
                        "owner2",
                        "startDate2",
                        "sitterPhoneNumber2",
                        "sitterEmail2",
                        "ownerPhoneNumber2",
                        "ownerEmail2",
                        20)
        );
    }
}
