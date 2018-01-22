package plate.plateandroid.App.PromotionList.Complements

import plate.plateandroid.App.PromotionList.PromotionModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable


/**
 * Created by rennerll on 11/25/17.
 */
interface PromotionAPI {
    @GET("/promotions/read/togo/{username}")
    fun readPromotionsToGo(@Path("username") username: String): Observable<Array<PromotionModel>>

    @GET("/promotions/read/going/{username}")
    fun readPromotionsGoing(@Path("username") username: String): Observable<Array<PromotionModel>>

    @POST("/requests/create/{username}/{promotion_id}/{request_code}")
    fun createRequest(@Path("username") username: String, @Path("promotion_id") promotion_id: String, @Path("request_code") request_code: String): Observable<Boolean>

    @POST("/promotions/create/{title}/{start_time}/{end_time}/{location}")
    fun createPromotion(@Path("title") title: String, @Path("start_time") start_time: String, @Path("end_time") end_time: String, @Path("location") location: String): Observable<String>
}