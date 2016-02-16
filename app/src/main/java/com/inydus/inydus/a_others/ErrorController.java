package com.inydus.inydus.a_others;

import android.content.Context;
import android.widget.Toast;

public class ErrorController {
    Context ctx;
    String errLog;

    public ErrorController(Context ctx) {
        this.ctx = ctx;
    }

    public ErrorController(Context ctx, String errLog) {
        this.ctx = ctx;
        this.errLog = errLog;
    }

    public void notifyNetworkError(){
        Toast.makeText(ctx, "인터넷 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
    }

}
