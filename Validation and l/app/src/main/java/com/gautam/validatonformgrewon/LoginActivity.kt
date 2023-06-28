package com.gautam.validatonformgrewon

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.gautam.validatonformgrewon.apimodal.RegisterResponse
import com.gautam.validatonformgrewon.apiretrofit.ApiClient
import com.gautam.validatonformgrewon.databinding.ActivityLoginBinding
import com.gautam.validatonformgrewon.entiy.AppDataBase
import com.gautam.validatonformgrewon.modal.RememberMe
import com.gautam.validatonformgrewon.modal.Users
import com.gautam.validatonformgrewon.param.LoginParam
import com.gautam.validatonformgrewon.shareprefrence.PrefManager
import com.gautam.validatonformgrewon.singuperrorbody.SingUpErrorBody
import com.gautam.validatonformgrewon.utils.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var db: AppDataBase
    lateinit var prefManager: PrefManager
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    var RC_SIGN_IN = 100
    lateinit var callbackManager: CallbackManager


    var googleLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            handleSignInResult(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDataBase.getInstance(this)
        prefManager = PrefManager(this)
        //FirebaseAuth.getInstance().signOut();
        googleLogin()
        setOnClickListner()


        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        callbackManager = CallbackManager.Factory.create()
        facebook()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        auth = FirebaseAuth.getInstance()

        if (prefManager.getRememberMe() != null) {
            binding.remember.isChecked = prefManager.getRememberMe()!!.isRemember
            if (prefManager.getRememberMe()!!.isRemember) {
                binding.logEmail.setText(prefManager.getRememberMe()?.email)
                binding.logLconpassworld.setText(prefManager.getRememberMe()?.passworld)
            }
        }


        binding.btLogin.setOnClickListener {
            val emaill = binding.logEmail.text.toString().trim()
            val passwordl = binding.logLconpassworld.text.toString().trim()

            if (emaill.isEmpty()) {
                binding.logEmail.error = getString(R.string.Enter_email)
                binding.logEmail.requestFocus()
            } else if (!Utils.isValidEmail(emaill)) {
                binding.logEmail.error = getString(R.string.Enter_valid_format_email)
                binding.logEmail.requestFocus()
//            } else if (passwordl.isEmpty()) {
//                binding.logLconpassworld.error = getString(R.string.enter_register_password)
//                binding.logLconpassworld.requestFocus()
//            } else if (!Utils.isValidpassword(passwordl)) {
//                binding.logLconpassworld.error =
//                    getString(R.string.Upper_case_with_lover_case_character)
//                binding.logLconpassworld.requestFocus()
            } else {

                val responch = LoginParam(
                    email = emaill, password = passwordl

                )
                apilogin(responch)
                binding.progressBar.isVisible = true

                //prefManager.saveLoginCredentials(responch)
                Log.e(" TAG", "onCreate: " + accessToken)
//
//                doLogin(
//                    binding.logEmail.text.toString().trim(),
//                    binding.logLconpassworld.text.toString().trim()
//                )

            }

        }
    }


    private fun apilogin(responch: LoginParam) {

        var api = ApiClient.init(this).userLogin(responch)
        Log.e("TAG", "api: " + api)

        api.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>, response: Response<RegisterResponse>
            ) {
                Log.e("TAG", "login: " + response.errorBody())
                Log.e("TAG", "onResponselogin: " + response.body())


                if (response.isSuccessful) {
                    var user = response.body()
                    if (user != null) {

                        prefManager.saveApiData(user)
                        prefManager.saveToken(let {
                            user.data.userApi
                        })
                        //  Toast.makeText(this@LoginActivity, "Api Successful", Toast.LENGTH_SHORT)
                        //     .show()
                        val data = intent.getIntExtra("the_wolff", 0)
                        var i = Intent(this@LoginActivity, MainActivity::class.java)
                        i.putExtra("the_wolff", data)
                        startActivity(i)
                        finish()
                    }
                } else {
                    try {
                        val gson = Gson()
                        val type = object : TypeToken<SingUpErrorBody>() {}.type
                        var errorResponse: SingUpErrorBody? =
                            gson.fromJson(response.errorBody()!!.charStream(), type)
                        Log.e("lucifer", "${errorResponse?.msg}")

                        binding.progressBar.isVisible = false

                        Toast.makeText(this@LoginActivity, errorResponse?.msg, Toast.LENGTH_LONG)
                            .show()
                    } catch (e: Exception) {

                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.cause)
                Toast.makeText(this@LoginActivity, "fail", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun setOnClickListner() {
        binding.tvForgetpassword.setOnClickListener {
            val i = Intent(this, ForgotpassActivity::class.java)
            startActivity(i)
            finish()
        }
        binding.tvLoginmobile.setOnClickListener {
            var i = Intent(this, PhoneSmsActivity::class.java)
            startActivity(i)
        }
        binding.tvYounotaccount.setOnClickListener {
            val i = Intent(this, SignupActivity::class.java)
            startActivity(i)
        }
    }

    private fun facebook() {
        binding.ivFacebook.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("email", "public_profile", "user_friends"));
        }
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Log.e("TAG", "onCancel: ")
                }

                override fun onError(error: FacebookException) {
                    Log.e("TAG", "onError: " + error.message)

                }

                override fun onSuccess(result: LoginResult) {
                    Log.e("TAG", "onSuccess: ")
                    handleFacebookAccessToken(result.accessToken)

                }

            })
    }

    private fun googleLogin() {

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("509451970679-26oq01c5hpvdmjcr4da95usup116jogc.apps.googleusercontent.com")
            .requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.ivGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            googleLogin.launch(signInIntent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        //  handleFacebookAccessToken()
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.e("PPPPPPP", "handleSignInResult: " + Gson().toJson(account))
            Log.e("PPPPPPP", "handleSignInResult: " + account.idToken)
            account?.let {
                val credential = GoogleAuthProvider.getCredential(it.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                        Log.e("TAG", "google: " + account.id)
                        val user = Users(
                            email = account.email.toString(),
                            google = account.id,
                            image = account.photoUrl.toString(),
                            name = account.givenName,
                            loginType = " Login by google"

                        )
                        prefManager.saveLoginCredentials(user)
                        db.userDao().insertRecord(user)
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: ApiException) {

        }
    }

    private fun doLogin(email: String, pass: String) {
        db.userDao().getUserList().observe(this) {
            var userData: Users? = null
            for (data in it) {
                if (data.email == email) {
                    userData = data
                }
            }
            if (userData != null) {
                if (userData.passworld == pass) {
                    prefManager.saveLoginCredentials(userData)
                    rememberMe()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.password_is_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.your_email_address_is_not_exist),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }


    private fun rememberMe() {
        val rememberMe = RememberMe(
            binding.logEmail.text.toString(),
            binding.logLconpassworld.text.toString(),
            binding.remember.isChecked
        )
        prefManager.saveRememberMe(rememberMe)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.e(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val user = auth.currentUser
                val useremail = auth.currentUser?.email
                val usereid = auth.currentUser?.uid
                val nemee = auth.currentUser?.displayName
                val useer = Users(

                    email = useremail, google = usereid, loginType = "login with facebook"
                )
                prefManager.saveLoginCredentials(useer)
                db.userDao().insertRecord(useer)
                updateUI(user)
                Log.e("facebookuii", "handleFacebookAccessToken: " + updateUI(user))
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                Toast.makeText(
                    baseContext, "Authentication failed.", Toast.LENGTH_SHORT
                ).show()
                updateUI(null)

            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        } else {
            Toast.makeText(this, "not successfully", Toast.LENGTH_SHORT).show()
        }

    }
}
