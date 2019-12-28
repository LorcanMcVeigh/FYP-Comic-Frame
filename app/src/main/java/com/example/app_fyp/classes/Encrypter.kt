package com.example.app_fyp.classes

import android.widget.EditText
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Base64
import java.security.InvalidKeyException
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.ShortBufferException
import javax.crypto.spec.SecretKeySpec

class Encrypter(){

    @Throws( IllegalBlockSizeException::class, InvalidKeyException::class, ShortBufferException::class)
    fun encrypt(strToEncrypt: EditText?, secret_key: String): String {
        var STE = strToEncrypt.toString()
        Security.addProvider(BouncyCastleProvider())
        var keyBytes: ByteArray

        try {
            keyBytes = secret_key.toByteArray(charset("UTF8"))
            val skey = SecretKeySpec(keyBytes, "AES")
            val input = STE.toByteArray(charset("UTF8"))

            synchronized(Cipher::class.java) {
                val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
                cipher.init(Cipher.ENCRYPT_MODE, skey)

                val cipherText = ByteArray(cipher.getOutputSize(input.size))
                var ctLength = cipher.update(
                    input, 0, input.size,
                    cipherText, 0
                )
                ctLength += cipher.doFinal(cipherText, ctLength)
                return String(
                    Base64.encode(cipherText)
                )
            }
        } catch ( e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch ( e: InvalidKeyException) {
            e.printStackTrace()
        } catch ( e: ShortBufferException) {
            e.printStackTrace()
        }

        return ""
    }
}