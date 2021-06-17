package cucumber.http.Sprint2;

import com.google.gson.Gson;
import cucumber.resource.accounts.StafffResource;
import cucumber.resource.business.ServiceResource;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetStateServiceHttp {
    private String url;
    private String path;
    private String method;
    private Response response;
    private List<String> services;
    private List<Integer> responseStatusCodes;

    public GetStateServiceHttp() {
        this.services = new ArrayList<String>();
        this.responseStatusCodes = new ArrayList<Integer>();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void make() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        for (int i = 0; i < this.services.size(); i++) {
            RequestBody body = RequestBody.create(this.services.get(i), mediaType);

            Request request = new Request.Builder()
                    .url(this.buildUrl())
                    .method(this.method, null)
                    .build();
            this.response = client.newCall(request).execute();
            this.responseStatusCodes.add(this.response.code());
        }
    }

    public StafffResource obtenerServices() throws IOException {
        Gson gson = new Gson();
        ResponseBody responseBody = this.response.body();
        return gson.fromJson(responseBody.string(), (Type) ServiceResource.class);
    }

    private String buildUrl(){
        return this.url + this.path;
    }
    public List<Integer> getResponseStatusCodes() {
        return this.responseStatusCodes;
    }
}
