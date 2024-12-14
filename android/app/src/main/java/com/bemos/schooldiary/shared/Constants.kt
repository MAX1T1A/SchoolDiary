package com.bemos.schooldiary.shared

object Constants {

    const val NAV_SIGN_UP_CHILD = "signUpChildren"
    const val NAV_SIGN_IN = "signIn"

    const val SERVER_IP = "109.206.236.143"
    const val BASE_URL = "http://$SERVER_IP/"
    private const val VERSION = "v1"
    private const val USERS = "users"
    const val USERS_REGISTER = "$USERS/api/$VERSION/register"
    const val USERS_AUTHENTICATE = "/$USERS/api/$VERSION/authenticate"

    const val SHARED_PREF_NAME_TOKEN_SAVER = "TokenSaver"
}