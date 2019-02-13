package com.tiaretdevgroup.openhackathon.java.utils

object Constants {
    const val HOST = "http://01d4d025.ngrok.io"
    const val FILE_BASE = "C:\\AppPharmacy"
    const val FILE_SALES = "$FILE_BASE\\sales.json"
    const val FILE_MALADIES = "$FILE_BASE\\maladies.json"
    const val FILE_TOKEN = "$FILE_BASE\\token.json"

    const val CLIENT = "$HOST/api/client"
    const val SALE_BLOCK = "$HOST/api/block/sale"
    const val MALADY_BLOCK = "$HOST/api/block/client"
    const val PHARMACY_SIGNUP = "$HOST/api/pharmacy"
    const val SALES = "$HOST/api/sale"
}