package com.icommunicate.apiCall;


/**
 * Ashish parmar
 * Interface for api response
 */
public interface IResult {
    void notifySuccess(String requestType, Object response);
    void notifyNetworkSuccess(String requestType);

}
