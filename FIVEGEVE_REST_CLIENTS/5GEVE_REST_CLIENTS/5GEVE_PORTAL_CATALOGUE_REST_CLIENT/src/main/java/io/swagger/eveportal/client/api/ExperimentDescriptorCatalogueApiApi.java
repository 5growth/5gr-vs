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


import io.swagger.eveportal.client.model.OnboardExpDescriptorRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExperimentDescriptorCatalogueApiApi {
    private ApiClient apiClient;

    public ExperimentDescriptorCatalogueApiApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ExperimentDescriptorCatalogueApiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for createExpDescriptorUsingPOST
     * @param request request (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call createExpDescriptorUsingPOSTCall(OnboardExpDescriptorRequest request, Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = request;

        // create path and map variables
        String localVarPath = "/portal/catalogue/expdescriptor";

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
    private com.squareup.okhttp.Call createExpDescriptorUsingPOSTValidateBeforeCall(OnboardExpDescriptorRequest request, Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'request' is set
        if (request == null) {
            throw new ApiException("Missing the required parameter 'request' when calling createExpDescriptorUsingPOST(Async)");
        }
        

        com.squareup.okhttp.Call call = createExpDescriptorUsingPOSTCall(request, authenticated, authorities0Authority, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Onboard Experiment Descriptor
     * 
     * @param request request (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String createExpDescriptorUsingPOST(OnboardExpDescriptorRequest request, Boolean authenticated, String authorities0Authority) throws ApiException {
        ApiResponse<String> resp = createExpDescriptorUsingPOSTWithHttpInfo(request, authenticated, authorities0Authority);
        return resp.getData();
    }

    /**
     * Onboard Experiment Descriptor
     * 
     * @param request request (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<String> createExpDescriptorUsingPOSTWithHttpInfo(OnboardExpDescriptorRequest request, Boolean authenticated, String authorities0Authority) throws ApiException {
        com.squareup.okhttp.Call call = createExpDescriptorUsingPOSTValidateBeforeCall(request, authenticated, authorities0Authority, null, null);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Onboard Experiment Descriptor (asynchronously)
     * 
     * @param request request (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call createExpDescriptorUsingPOSTAsync(OnboardExpDescriptorRequest request, Boolean authenticated, String authorities0Authority, final ApiCallback<String> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = createExpDescriptorUsingPOSTValidateBeforeCall(request, authenticated, authorities0Authority, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getAllExpDescriptorsUsingGET
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAllExpDescriptorsUsingGETCall(Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/portal/catalogue/expdescriptor";

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
    private com.squareup.okhttp.Call getAllExpDescriptorsUsingGETValidateBeforeCall(Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        

        com.squareup.okhttp.Call call = getAllExpDescriptorsUsingGETCall(authenticated, authorities0Authority, progressListener, progressRequestListener);
        return call;

    }

    /**
     * getAllExpDescriptors
     * 
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Object getAllExpDescriptorsUsingGET(Boolean authenticated, String authorities0Authority) throws ApiException {
        ApiResponse<Object> resp = getAllExpDescriptorsUsingGETWithHttpInfo(authenticated, authorities0Authority);
        return resp.getData();
    }

    /**
     * getAllExpDescriptors
     * 
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Object> getAllExpDescriptorsUsingGETWithHttpInfo(Boolean authenticated, String authorities0Authority) throws ApiException {
        com.squareup.okhttp.Call call = getAllExpDescriptorsUsingGETValidateBeforeCall(authenticated, authorities0Authority, null, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * getAllExpDescriptors (asynchronously)
     * 
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAllExpDescriptorsUsingGETAsync(Boolean authenticated, String authorities0Authority, final ApiCallback<Object> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = getAllExpDescriptorsUsingGETValidateBeforeCall(authenticated, authorities0Authority, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getExpDescriptorUsingGET
     * @param expdId expdId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getExpDescriptorUsingGETCall(String expdId, Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/portal/catalogue/expdescriptor/{expdId}"
            .replaceAll("\\{" + "expdId" + "\\}", apiClient.escapeString(expdId.toString()));

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
    private com.squareup.okhttp.Call getExpDescriptorUsingGETValidateBeforeCall(String expdId, Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'expdId' is set
        if (expdId == null) {
            throw new ApiException("Missing the required parameter 'expdId' when calling getExpDescriptorUsingGET(Async)");
        }
        

        com.squareup.okhttp.Call call = getExpDescriptorUsingGETCall(expdId, authenticated, authorities0Authority, progressListener, progressRequestListener);
        return call;

    }

    /**
     * getExpDescriptor
     * 
     * @param expdId expdId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Object getExpDescriptorUsingGET(String expdId, Boolean authenticated, String authorities0Authority) throws ApiException {
        ApiResponse<Object> resp = getExpDescriptorUsingGETWithHttpInfo(expdId, authenticated, authorities0Authority);
        return resp.getData();
    }

    /**
     * getExpDescriptor
     * 
     * @param expdId expdId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Object> getExpDescriptorUsingGETWithHttpInfo(String expdId, Boolean authenticated, String authorities0Authority) throws ApiException {
        com.squareup.okhttp.Call call = getExpDescriptorUsingGETValidateBeforeCall(expdId, authenticated, authorities0Authority, null, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * getExpDescriptor (asynchronously)
     * 
     * @param expdId expdId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getExpDescriptorUsingGETAsync(String expdId, Boolean authenticated, String authorities0Authority, final ApiCallback<Object> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = getExpDescriptorUsingGETValidateBeforeCall(expdId, authenticated, authorities0Authority, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
