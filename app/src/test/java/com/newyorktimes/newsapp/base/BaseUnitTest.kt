package com.newyorktimes.newsapp.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import com.lavaira.checklistapp.ChecklistApplication
import com.lavaira.checklistapp.di.modules.NetworkModule
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.Retrofit

/****
 * Base class for all types of unit test and instrumentation tests
 * All the  initial mocks required for enabling unit testing are done in this class.
 * The class will be using Mockito and PowerMock for mocking purposes
 * Author: Lajesh Dineshkumar
 *****/
@RunWith(PowerMockRunner::class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(
    Log::class,
    Handler::class,
    Looper::class,
    TextUtils::class,
    ChecklistApplication::class
)
abstract class BaseUnitTest {

    lateinit var context: Context
    lateinit var sharedPreferences : SharedPreferences
    lateinit var mockWebServer: MockWebServer

    lateinit var retrofit: Retrofit
    lateinit var testScheduler: TestScheduler



    @Before
    @Throws(Exception::class)
    open fun setUp() {
        // Initializes the mock environment
        MockitoAnnotations.initMocks(this)

        // Initializes the mock webserver
        mockWebServer = MockWebServer()
        startMockWebserver()

        // Mocks the generic android dependencies such as Context, Looper, etc.
        mockAndroidDependencies()

        // Mocks android logs
        mockLogs()

        // Mocks shared preferences
        mockSharedPreference()

        // Initializes the retrofit dependencies
        initDependencies()

        // Sets the RXJava schedulers for unit tests
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ Schedulers.trampoline()}
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    open fun tearDown(){
        stopMockWebserver()
    }

    /**
     * Method which starts the mockwebserver
     */
    private fun startMockWebserver(){
        mockWebServer.start(8081)
    }

    /**
     * Method which stops the mock webserver
     */
    private fun stopMockWebserver(){
        mockWebServer.shutdown()
    }


    /**
     * This function will mock all the android Log related dependencies
     */
    private fun mockLogs(){
        PowerMockito.mockStatic(Log::class.java)
        val logAnswer = Answer<Void> { invocation ->
            val tag = invocation.arguments[0] as String
            val msg = invocation.arguments[1] as String
            println(invocation.method.name.toUpperCase() + "/[" + tag + "] " + msg)
            null
        }
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "d",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "i",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "w",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "e",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "wtf",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )

        PowerMockito.doAnswer { invocation ->
            val s = invocation.arguments[0] as String
            s.isEmpty()
        }.`when`(TextUtils::class.java, "isEmpty", ArgumentMatchers.anyString())

    }

    /**
     * This function will mock all the SharedPreference related dependencies
     */
    private fun mockSharedPreference(){
        PowerMockito.`when`(
            sharedPreferences.getString(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn("")
        PowerMockito.`when`(
            sharedPreferences.getLong(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyLong()
            )
        ).thenReturn(0L)
        PowerMockito.`when`(
            sharedPreferences.getInt(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(0)
        PowerMockito.`when`(
            sharedPreferences.getBoolean(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyBoolean()
            )
        ).thenReturn(false)
        PowerMockito.`when`(
            sharedPreferences.getFloat(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyFloat()
            )
        ).thenReturn(0f)
        PowerMockito.`when`(
            context.getSharedPreferences(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(sharedPreferences)
        PowerMockito.`when`(context.packageName).thenReturn("com.msil.sharedmobility.selfdrive")
    }

    /**
     * This method initializes the retrofit module
     */
    private fun initDependencies(){

        val networkModule = NetworkModule()
        val createLoggingInterceptor = networkModule.provideLogging()
        val httpClient = networkModule.provideOkHttpClient(createLoggingInterceptor)
        val serverUrl = mockWebServer.url("/").toString()
        retrofit = networkModule.provideRetrofit(serverUrl,httpClient)
    }

    private fun mockAndroidDependencies(){
        context = PowerMockito.mock(Context::class.java)
        sharedPreferences = PowerMockito.mock(SharedPreferences::class.java)
        testScheduler = TestScheduler()
        ChecklistApplication.instance  = Mockito.mock(ChecklistApplication::class.java)
        PowerMockito.mockStatic(Looper::class.java)
        PowerMockito.mockStatic(Handler::class.java)
        PowerMockito.mockStatic(TextUtils::class.java)
        `when`(ChecklistApplication.instance!!.applicationContext).thenReturn(context)
        `when`(ChecklistApplication.applicationContext()).thenReturn(context)
        PowerMockito.`when`(Looper.getMainLooper()).thenReturn(null)
        PowerMockito.whenNew(Handler::class.java).withAnyArguments().thenReturn(null)
    }
}