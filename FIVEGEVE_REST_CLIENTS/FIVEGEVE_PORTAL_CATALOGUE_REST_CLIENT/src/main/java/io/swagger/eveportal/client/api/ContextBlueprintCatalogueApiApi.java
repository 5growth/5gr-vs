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


import io.swagger.eveportal.client.model.CtxBlueprintInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextBlueprintCatalogueApiApi {
    private ApiClient apiClient;

    public ContextBlueprintCatalogueApiApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ContextBlueprintCatalogueApiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getAllCtxBlueprintsUsingGET
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAllCtxBlueprintsUsingGETCall(Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/portal/catalogue/ctxblueprint";

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
    private com.squareup.okhttp.Call getAllCtxBlueprintsUsingGETValidateBeforeCall(Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        

        com.squareup.okhttp.Call call = getAllCtxBlueprintsUsingGETCall(authenticated, authorities0Authority, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get ALL CtxBlueprints
     * 
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return List&lt;CtxBlueprintInfo&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<CtxBlueprintInfo> getAllCtxBlueprintsUsingGET(Boolean authenticated, String authorities0Authority) throws ApiException {
        ApiResponse<List<CtxBlueprintInfo>> resp = getAllCtxBlueprintsUsingGETWithHttpInfo(authenticated, authorities0Authority);
        return resp.getData();
    }

    /**
     * Get ALL CtxBlueprints
     * 
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return ApiResponse&lt;List&lt;CtxBlueprintInfo&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<CtxBlueprintInfo>> getAllCtxBlueprintsUsingGETWithHttpInfo(Boolean authenticated, String authorities0Authority) throws ApiException {
        com.squareup.okhttp.Call call = getAllCtxBlueprintsUsingGETValidateBeforeCall(authenticated, authorities0Authority, null, null);
        Type localVarReturnType = new TypeToken<List<CtxBlueprintInfo>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get ALL CtxBlueprints (asynchronously)
     * 
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAllCtxBlueprintsUsingGETAsync(Boolean authenticated, String authorities0Authority, final ApiCallback<List<CtxBlueprintInfo>> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = getAllCtxBlueprintsUsingGETValidateBeforeCall(authenticated, authorities0Authority, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<CtxBlueprintInfo>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getCtxBlueprintUsingGET
     * @param ctxbId ctxbId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getCtxBlueprintUsingGETCall(String ctxbId, Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/portal/catalogue/ctxblueprint/{ctxbId}"
            .replaceAll("\\{" + "ctxbId" + "\\}", apiClient.escapeString(ctxbId.toString()));

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
    private com.squareup.okhttp.Call getCtxBlueprintUsingGETValidateBeforeCall(String ctxbId, Boolean authenticated, String authorities0Authority, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'ctxbId' is set
        if (ctxbId == null) {
            throw new ApiException("Missing the required parameter 'ctxbId' when calling getCtxBlueprintUsingGET(Async)");
        }
        

        com.squareup.okhttp.Call call = getCtxBlueprintUsingGETCall(ctxbId, authenticated, authorities0Authority, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get CtxBlueprint
     * 
     * @param ctxbId ctxbId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return CtxBlueprintInfo
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public CtxBlueprintInfo getCtxBlueprintUsingGET(String ctxbId, Boolean authenticated, String authorities0Authority) throws ApiException {
        ApiResponse<CtxBlueprintInfo> resp = getCtxBlueprintUsingGETWithHttpInfo(ctxbId, authenticated, authorities0Authority);
        return resp.getData();
    }

    /**
     * Get CtxBlueprint
     * 
     * @param ctxbId ctxbId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @return ApiResponse&lt;CtxBlueprintInfo&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<CtxBlueprintInfo> getCtxBlueprintUsingGETWithHttpInfo(String ctxbId, Boolean authenticated, String authorities0Authority) throws ApiException {
        com.squareup.okhttp.Call call = getCtxBlueprintUsingGETValidateBeforeCall(ctxbId, authenticated, authorities0Authority, null, null);
        Type localVarReturnType = new TypeToken<CtxBlueprintInfo>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get CtxBlueprint (asynchronously)
     * 
     * @param ctxbId ctxbId (required)
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getCtxBlueprintUsingGETAsync(String ctxbId, Boolean authenticated, String authorities0Authority, final ApiCallback<CtxBlueprintInfo> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = getCtxBlueprintUsingGETValidateBeforeCall(ctxbId, authenticated, authorities0Authority, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<CtxBlueprintInfo>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
