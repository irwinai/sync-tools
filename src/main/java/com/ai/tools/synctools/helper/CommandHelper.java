package com.ai.tools.synctools.helper;

import org.springframework.beans.factory.annotation.Value;

public abstract class CommandHelper {
    @Value("${sync.path.base}")
    protected String basePath;

    @Value("${sync.repo.url}")
    protected String repoUrl;

    @Value("${sync.path.repo}")
    protected String repoPath;

    public abstract void exec();
}
