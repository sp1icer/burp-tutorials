package burp;

import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender
{
    public void registerExtenderCallbacks (IBurpExtenderCallbacks callbacks) {
        callbacks.setExtensionName("Hello World Extension");

        PrintWriter stdout = new PrintWriter(callbacks.getStdout(), true);
        PrintWriter stderr = new PrintWriter(callbacks.getStderr(), true);

        stdout.println("hello to the output homies");
        stdout.println("hello errors my old friend");

        callbacks.issueAlert("There's an alert in my boot!");

        throw new RuntimeException("Woah man, we totally just crashed man");
    }
}