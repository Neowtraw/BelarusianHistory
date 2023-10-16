package com.codingub.belarusianhistory.data.remote.network

import java.lang.Exception

class ServerException (val errorCode: Int? = 0, val msg: String? = ""): Exception()
