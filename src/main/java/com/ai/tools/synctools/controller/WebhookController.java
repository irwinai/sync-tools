package com.ai.tools.synctools.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@Slf4j
public class WebhookController {
    private static final String REMOTE_URL = "https://github.com/irwinai/JavaInterview.git";

    @PostMapping("/webhook")
    public void webhook(@RequestBody JsonNode jsonNode) throws Exception {
        File localPath = new File("/root/docs/JavaInterview");

        // 不管那么多，先删了再说
        FileUtils.deleteDirectory(localPath);

        log.info("Starting clone repository...");

        //直接 clone 代码
        try (Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localPath)
                .setProgressMonitor(new SimpleProgressMonitor())
                .call()) {
            log.info("Clone repository success: " + result.getRepository().getDirectory());
        } catch (Exception e) {
            log.error("Clone repository failed", e);
        }
    }

    private static class SimpleProgressMonitor implements ProgressMonitor {
        @Override
        public void start(int totalTasks) {
            log.info("Starting work on " + totalTasks + " tasks");
        }

        @Override
        public void beginTask(String title, int totalWork) {
            log.info(" BeginTask " + title + ": " + totalWork);
        }

        @Override
        public void update(int completed) {
            log.info("update Task " + completed);
        }

        @Override
        public void endTask() {
            log.info("endTask");
        }

        @Override
        public boolean isCancelled() {
            return false;
        }
    }
}
