package com.example.app_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.io.IOException
import javax.crypto.ShortBufferException
import java.security.NoSuchAlgorithmException
import javax.crypto.NoSuchPaddingException
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException
import java.io.UnsupportedEncodingException
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Base64
import java.security.InvalidKeyException
import java.security.Security

class RegisterActivity : AppCompatActivity() {
    private var name: EditText? = null
    private var regbtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        name = findViewById<View>(R.id.name) as EditText
        regbtn = findViewById<View>(R.id.regbtn) as Button
        val encryptemail = encrypt(findViewById<View>(R.id.email) as EditText, name.toString())
        val encryptpassword = encrypt(findViewById<View>(R.id.password) as EditText, encryptemail)


        regbtn!!.setOnClickListener {
            try {
                register(encryptemail, encryptpassword)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    private fun register(email: String, password: String){

    }

    @Throws(UnsupportedEncodingException::class, IllegalBlockSizeException::class, BadPaddingException::class,
        InvalidKeyException::class, NoSuchPaddingException::class, NoSuchAlgorithmException::class, ShortBufferException::class)
    private fun encrypt(strToEncrypt: EditText?, secret_key: String): String {
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
        } catch (uee: UnsupportedEncodingException) {
            uee.printStackTrace()
        } catch (ibse: IllegalBlockSizeException) {
            ibse.printStackTrace()
        } catch (bpe: BadPaddingException) {
            bpe.printStackTrace()
        } catch (ike: InvalidKeyException) {
            ike.printStackTrace()
        } catch (nspe: NoSuchPaddingException) {
            nspe.printStackTrace()
        } catch (nsae: NoSuchAlgorithmException) {
            nsae.printStackTrace()
        } catch (e: ShortBufferException) {
            e.printStackTrace()
        }

        return ""
    }
}