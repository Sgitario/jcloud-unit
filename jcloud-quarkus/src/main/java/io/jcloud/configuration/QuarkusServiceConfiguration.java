package io.jcloud.configuration;

public final class QuarkusServiceConfiguration {
    private String expectedLog = "Installed features";

    public String getExpectedLog() {
        return expectedLog;
    }

    public void setExpectedLog(String expectedLog) {
        this.expectedLog = expectedLog;
    }
}
