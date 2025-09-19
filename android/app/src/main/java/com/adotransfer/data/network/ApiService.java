package com.adotransfer.data.network;

import com.adotransfer.data.model.ApiResponse;
import com.adotransfer.data.model.Transaction;
import com.adotransfer.data.model.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ApiService {
    
    // Authentification
    @POST("auth/register")
    Call<ApiResponse<User>> register(@Body Map<String, String> userData);
    
    @POST("auth/login")
    Call<ApiResponse<Map<String, Object>>> login(@Body Map<String, String> credentials);
    
    @POST("auth/verify-phone")
    Call<ApiResponse<String>> verifyPhone(@Query("phoneNumber") String phoneNumber);
    
    @POST("auth/verify-otp")
    Call<ApiResponse<String>> verifyOtp(@Query("phoneNumber") String phoneNumber, 
                                       @Query("otp") String otp);
    
    // Utilisateurs
    @GET("users/profile")
    Call<ApiResponse<User>> getProfile();
    
    @PUT("users/profile")
    Call<ApiResponse<User>> updateProfile(@Body Map<String, String> userData);
    
    @POST("users/set-pin")
    Call<ApiResponse<String>> setPin(@Query("pin") String pin);
    
    @POST("users/verify-pin")
    Call<ApiResponse<String>> verifyPin(@Query("pin") String pin);
    
    // Comptes
    @GET("accounts/balance")
    Call<ApiResponse<BigDecimal>> getBalance();
    
    @GET("accounts/details")
    Call<ApiResponse<Map<String, Object>>> getAccountDetails();
    
    @POST("accounts/deposit")
    Call<ApiResponse<String>> deposit(@Query("amount") BigDecimal amount);
    
    @POST("accounts/withdraw")
    Call<ApiResponse<String>> withdraw(@Query("amount") BigDecimal amount);
    
    // Transactions
    @POST("transactions/transfer")
    Call<ApiResponse<Transaction>> transferMoney(@Body Map<String, Object> transferData);
    
    @GET("transactions")
    Call<ApiResponse<Map<String, Object>>> getTransactions(@Query("page") int page, 
                                                          @Query("size") int size);
    
    @GET("transactions/sent")
    Call<ApiResponse<Map<String, Object>>> getSentTransactions(@Query("page") int page, 
                                                              @Query("size") int size);
    
    @GET("transactions/received")
    Call<ApiResponse<Map<String, Object>>> getReceivedTransactions(@Query("page") int page, 
                                                                  @Query("size") int size);
    
    @GET("transactions/{transactionId}")
    Call<ApiResponse<Transaction>> getTransactionById(@Path("transactionId") String transactionId);
    
    @POST("transactions/{transactionId}/cancel")
    Call<ApiResponse<Transaction>> cancelTransaction(@Path("transactionId") String transactionId);
}
