package ru.eremenko.demo;

public class BrowserOpener {

    private String os;
    private Runtime runtime;

    public BrowserOpener() {

        os = System.getProperty("os.name").toLowerCase();
        runtime = Runtime.getRuntime();
    }

    public void openLinkInBrowser(String url) {

        try {
            if (isWindows()) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (isMac()) {
                runtime.exec("open " + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isWindows() {
        return os.contains("win");
    }

    private boolean isMac() {
        return os.contains("mac");
    }


}