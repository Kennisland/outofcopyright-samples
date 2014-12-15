import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class api {
    private String URL;
    private String version;
    private String[] parameters;
    private String result;

    public String getURL() {
        return URL;
    }

    public String getVersion() {
        return version;
    }

    public String[] getParameters() {
        return parameters;
    }

    public String getResult() {
        return result;
    }
}
