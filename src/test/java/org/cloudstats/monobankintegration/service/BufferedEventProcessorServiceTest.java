package org.cloudstats.monobankintegration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class BufferedEventProcessorServiceTest {
    @Autowired
    private BufferedEventProcessorService bufferedEventProcessorService;

    @MockBean
    private GoogleAppsScriptService googleAppsScriptService;

    @Test
    public void shouldDoNothingIfBufferIsEmpty() {}

    @Test
    public void shouldExecuteScriptIfBufferIsNotEmpty() {
//        verify(googleAppsScriptService).executeScript();
    }
}
