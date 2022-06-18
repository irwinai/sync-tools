package com.ai.tools.synctools.helper;

import com.ai.tools.synctools.helper.CommandHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class GitCommandsHelper extends CommandHelper {

    @Override
    public void exec() {
        File localPath = new File(repoPath);

        // 不管那么多，先删了再说
        try {
            FileUtils.deleteDirectory(localPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("Starting clone repository...");

        //直接 clone 代码
        try (Git result = Git.cloneRepository()
                .setURI(repoUrl)
                .setDirectory(localPath)
                .setProgressMonitor(new SimpleProgressMonitor())
                .call()) {
            log.info("Clone repository success: {}", result.getRepository().getDirectory());
        } catch (Exception e) {
            log.error("Clone repository failed", e);
        }
    }

    private static class SimpleProgressMonitor implements ProgressMonitor {
        @Override
        public void start(int totalTasks) {
            log.info("Starting work on {} tasks", totalTasks);
        }

        @Override
        public void beginTask(String title, int totalWork) {
            log.info(" BeginTask {}:{}", title, totalWork);
        }

        @Override
        public void update(int completed) {
            log.info("update Task {}", completed);
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
