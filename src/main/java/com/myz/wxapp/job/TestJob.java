package com.myz.wxapp.job;

import com.myz.inf.batch.JobEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * TestQuartzJob
 * Created by myz
 * Date 2020/12/14 15:42
 */
@Service
public class TestJob {
    private static final Logger logger = LoggerFactory.getLogger(TestJob.class);

    private long counter = 0;

    @JobEntry(name="TestJob", cron = "0/10 * * * * ?")
    public void run() {
        logger.info("counter is {}", counter++);
    }
}
