package com.fe.glideloadimageprogress.progress;

public interface ProgressListener {

    void progress(long bytesRead, long contentLength, boolean done);

}
