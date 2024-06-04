package otus.homework.coroutines

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class CatsPresenter(
    private val catsService: CatsService
){

    private var _catsView: ICatsView? = null

    val scope = PresenterScope()

    fun onInitComplete() {
        scope.launch {
            try {
                Log.d("CatsPresenter", this.coroutineContext.toString())
                val result = catsService.getCatFact()
                _catsView?.populate(result)
            } catch (e: SocketTimeoutException) {
                _catsView?.showToast("Не удалось получить ответ от сервером")
            } catch (e: Exception) {
                _catsView?.showToast(e.message.toString())
            }
        }
    }

    fun attachView(catsView: ICatsView) {
        _catsView = catsView
    }

    fun detachView() {
        _catsView = null
    }
}