/*
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.eveportal.client.api;

import io.swagger.eveportal.client.ApiCallback;
import io.swagger.eveportal.client.ApiClient;
import io.swagger.eveportal.client.ApiException;
import io.swagger.eveportal.client.ApiResponse;
import io.swagger.eveportal.client.Configuration;
import io.swagger.eveportal.client.Pair;
import io.swagger.eveportal.client.ProgressRequestBody;
import io.swagger.eveportal.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import io.swagger.eveportal.client.model.OnboardVsDescriptorRequest;
import io.swagger.eveportal.client.model.VsDescriptor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerticalServiceDescriptorCatalogueApiApi {
    private ApiClient apiClient;

    public VerticalServiceDescriptorCatalogueApiApi() {
        this(Configuration.getDefaultApiClient());
    }

    public VerticalServiceDescriptorCatalogueApiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for createVsDescriptorUsingPOST
     * @param request request (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call createVsDescriptorUsingPOSTCall(OnboardVsDescriptorRequest request, Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = request;

        // create path and map variables
        String localVarPath = "/portal/catalogue/vsdescriptor";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (authenticated != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("authenticated", authenticated));
        if (authorities0Authority != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("authorities[0].authority", authorities0Authority));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call createVsDescriptorUsingPOSTValidateBeforeCall(OnboardVsDescriptorRequest request, Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'request' is set
        if (request == null) {
            throw new ApiException("Missing the required parameter 'request' when calling createVsDescriptorUsingPOST(Async)");
        }
        

        com.squareup.okhttp.Call call = createVsDescriptorUsingPOSTCall(request, authenticated, authorities0Authority, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Onboard a new Vertical Service Descriptor
     * 
     * @param request request (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String createVsDescriptorUsingPOST(OnboardVsDescriptorRequest request, Boolean authenticated, String authorities0Authority) throws ApiException {
        ApiResponse<String> resp = createVsDescriptorUsingPOSTWithHttpInfo(request, authenticated, authorities0Authority);
        return resp.getData();
    }

    /**
     * Onboard a new Vertical Service Descriptor
     * 
     * @param request request (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<String> createVsDescriptorUsingPOSTWithHttpInfo(OnboardVsDescriptorRequest request, Boolean authenticated, String authorities0Authority) throws ApiException {
        com.squareup.okhttp.Call call = createVsDescriptorUsingPOSTValidateBeforeCall(request, authenticated, authorities0Authority, null, null);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Onboard a new Vertical Service Descriptor (asynchronously)
     * 
     * @param request request (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call createVsDescriptorUsingPOSTAsync(OnboardVsDescriptorRequest request, Boolean authenticated, String authorities0Authority, final ApiCallback<String> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = createVsDescriptorUsingPOSTValidateBeforeCall(request, authenticated, authorities0Authority, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getAllVsDescriptorsUsingGET
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAllVsDescriptorsUsingGETCall(Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/portal/catalogue/vsdescriptor";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (authenticated != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("authenticated", authenticated));
        if (authorities0Authority != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("authorities[0].authority", authorities0Authority));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getAllVsDescriptorsUsingGETValidateBeforeCall(Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        

        com.squareup.okhttp.Call call = getAllVsDescriptorsUsingGETCall(authenticated, authorities0Authority, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Query ALL the Vertical Service Descriptor
     * 
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return List&lt;VsDescriptor&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<VsDescriptor> getAllVsDescriptorsUsingGET(Boolean authenticated, String authorities0Authority) throws ApiException {
        ApiResponse<List<VsDescriptor>> resp = getAllVsDescriptorsUsingGETWithHttpInfo(authenticated, authorities0Authority);
        return resp.getData();
    }

    /**
     * Query ALL the Vertical Service Descriptor
     * 
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return ApiResponse&lt;List&lt;VsDescriptor&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<VsDescriptor>> getAllVsDescriptorsUsingGETWithHttpInfo(Boolean authenticated, String authorities0Authority) throws ApiException {
        com.squareup.okhttp.Call call = getAllVsDescriptorsUsingGETValidateBeforeCall(authenticated, authorities0Authority, null, null);
        Type localVarReturnType = new TypeToken<List<VsDescriptor>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Query ALL the Vertical Service Descriptor (asynchronously)
     * 
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAllVsDescriptorsUsingGETAsync(Boolean authenticated, String authorities0Authority, final ApiCallback<List<VsDescriptor>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getAllVsDescriptorsUsingGETValidateBeforeCall(authenticated, authorities0Authority, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<VsDescriptor>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getVsDescriptorUsingGET
     * @param vsdId vsdId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getVsDescriptorUsingGETCall(String vsdId, Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/portal/catalogue/vsdescriptor/{vsdId}"
            .replaceAll("\\{" + "vsdId" + "\\}", apiClient.escapeString(vsdId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (authenticated != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("authenticated", authenticated));
        if (authorities0Authority != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("authorities[0].authority", authorities0Authority));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getVsDescriptorUsingGETValidateBeforeCall(String vsdId, Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'vsdId' is set
        if (vsdId == null) {
            throw new ApiException("Missing the required parameter 'vsdId' when calling getVsDescriptorUsingGET(Async)");
        }
        

        com.squareup.okhttp.Call call = getVsDescriptorUsingGETCall(vsdId, authenticated, authorities0Authority, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Query a Vertical Service Descriptor with a given ID
     * 
     * @param vsdId vsdId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return VsDescriptor
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public VsDescriptor getVsDescriptorUsingGET(String vsdId, Boolean authenticated, String authorities0Authority) throws ApiException {
        ApiResponse<VsDescriptor> resp = getVsDescriptorUsingGETWithHttpInfo(vsdId, authenticated, authorities0Authority);
        return resp.getData();
    }

    /**
     * Query a Vertical Service Descriptor with a given ID
     * 
     * @param vsdId vsdId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return ApiResponse&lt;VsDescriptor&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<VsDescriptor> getVsDescriptorUsingGETWithHttpInfo(String vsdId, Boolean authenticated, String authorities0Authority) throws ApiException {
        com.squareup.okhttp.Call call = getVsDescriptorUsingGETValidateBeforeCall(vsdId, authenticated, authorities0Authority, null, null);
        Type localVarReturnType = new TypeToken<VsDescriptor>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Query a Vertical Service Descriptor with a given ID (asynchronously)
     * 
     * @param vsdId vsdId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getVsDescriptorUsingGETAsync(String vsdId, Boolean authenticated, String authorities0Authority, final ApiCallback<VsDescriptor> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getVsDescriptorUsingGETValidateBeforeCall(vsdId, authenticated, authorities0Authority, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<VsDescriptor>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
