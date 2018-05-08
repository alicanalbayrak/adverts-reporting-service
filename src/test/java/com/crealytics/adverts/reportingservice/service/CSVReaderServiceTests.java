package com.crealytics.adverts.reportingservice.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author alican.albayrak
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class CSVReaderServiceTests {

    @Autowired
    CSVReaderService csvReaderService;

    @Test
    public void testFileRead() {
        try {
            Resource[] resources = csvReaderService.getCSVResources();
            csvReaderService.deserializeFiles(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
