package burp;

import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender, IHttpListener, IProxyListener, IScannerListener, IExtensionStateListener
{

    private IBurpExtenderCallbacks callbacks;
    private PrintWriter stdout;

    public void registerExtenderCallbacks (IBurpExtenderCallbacks callbacks) {
        // Set up callbacks and the output stream
        this.callbacks = callbacks;
        stdout = new PrintWriter(callbacks.getStdout(), true);

        // Set up the different listeners.
        callbacks.setExtensionName("Event Listener Demo");
        callbacks.registerHttpListener(this);
        callbacks.registerProxyListener(this);
        callbacks.registerScannerListener(this);
        callbacks.registerExtensionStateListener(this);
    }

    @Override
    public void extensionUnloaded() {
        stdout.println("Extension was unloaded!");
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse requestResponse) {
        stdout.println(
                (messageIsRequest ? "HTTP request to: " : "HTTP response from: ") +
                        requestResponse.getHttpService() +
                        " [" + callbacks.getToolName(toolFlag) + "]"
        );
    }

    @Override
    public void processProxyMessage(boolean messageIsRequest, IInterceptedProxyMessage proxyMessage) {
        stdout.println(
                (messageIsRequest ? "Proxy request to: " : "Proxy response from: ") +
                        proxyMessage.getMessageInfo().getHttpService()
        );
    }

    @Override
    public void newScanIssue(IScanIssue scanIssue) {
        stdout.println("New scan issue: " + scanIssue.getIssueName());
    }
}