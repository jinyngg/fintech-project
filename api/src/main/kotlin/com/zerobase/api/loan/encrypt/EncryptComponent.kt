package com.zerobase.api.loan.encrypt

import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

// TODO 암호화 어노테이션 만드는 것 찾아보기
@Component
class EncryptComponent {
    
    // KEY 관리를 하지 않고 생성해서 사용
    companion object {
        private const val secretKey = "38451189384511893845118938451189"
    }

    // 암호화, 복호화에 필요한 인코더와 디코더 생성
    private val encoder = Base64.getEncoder()
    private val decoder = Base64.getDecoder()

    fun encryptString(encryptString: String): String {
        val encryptedString = cipherPkcs5(Cipher.ENCRYPT_MODE, secretKey).doFinal(encryptString.toByteArray(Charsets.UTF_8))

        return String(encoder.encode(encryptedString))
    }

    fun decryptString(decryptString: String): String {
        val byteString = decoder.decode(decryptString.toByteArray(Charsets.UTF_8))

        return String(cipherPkcs5(Cipher.DECRYPT_MODE, secretKey).doFinal(byteString))
    }

    fun cipherPkcs5(opMode: Int, secretKey: String): Cipher {
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val sk = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
        val iv = IvParameterSpec(secretKey.substring(0, 16).toByteArray(Charsets.UTF_8))
        c.init(opMode, sk, iv)
        return c
    }
}