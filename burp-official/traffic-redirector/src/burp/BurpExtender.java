package burp;

public class BurpExtender implements IBurpExtender, IHttpListener {

    private static final String HOST_FROM = "host1.sp1icer.dev";
    private static final String HOST_TO = "host2.sp1icer.dev";

    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;
    private final String extName = "Traffic Redirector";

    @Override
    public void registerExtenderCallbacks (IBurpExtenderCallbacks callbacks) {
        helpers = callbacks.getHelpers();
        callbacks.setExtensionName(extName);
        callbacks.registerHttpListener(this);
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse requestResponse) {
        if (messageIsRequest) {
            IHttpService httpSvc = requestResponse.getHttpService();

            // If message is a request AND matches our host we want to change,
            //      then we swap it over to the new host and forward it on.
            if (HOST_FROM.equalsIgnoreCase(httpSvc.getHost())) {
                requestResponse.setHttpService(helpers.buildHttpService(
                        HOST_TO, httpSvc.getPort(), httpSvc.getProtocol()
                ));
            }
        }
    }
}