package plate.plateandroid.App.LoginPage.Complements

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable

/**
 * Created by rennerll on 11/25/17.
 */
interface LoginAPI {
    @POST("/users/create/{username}")
    fun registerUser(@Path("username") username: String): Observable<Boolean>

    @GET("/users/read/{username}")
    fun checkUser(@Path("username") username: String): Observable<Boolean>
}