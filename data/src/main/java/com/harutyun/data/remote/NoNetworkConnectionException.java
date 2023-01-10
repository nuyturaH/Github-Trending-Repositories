package com.harutyun.data.remote;

import java.io.IOException;

public class NoNetworkConnectionException extends IOException {

    @Override
    public String getMessage() {
        return "No network connection";
    }

}
