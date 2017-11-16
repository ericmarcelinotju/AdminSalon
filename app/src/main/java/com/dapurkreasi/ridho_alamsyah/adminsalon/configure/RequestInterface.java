package com.dapurkreasi.ridho_alamsyah.adminsalon.configure;

import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerRequest;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Ridho_Alamsyah on 29/06/2017.
 */

public interface RequestInterface {

    @POST("SalonWawat/Admin/")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
