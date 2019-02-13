package blockchain.blocks

import utils.HashUtils
import java.util.Date

abstract class Block(val number: Long,
                     val nonce: Int,
                     val timestamp: Long,
                     val previousHash: String) {
    companion object {
        const val NUMBER = "number"
        const val PREVIOUS_HASH = "previousHash"
        const val HASH = "hash"
        const val NONCE = "nonce"
        const val TIMESTAMP = "timestamp"
    }


    val hash: String
        get() = HashUtils.hash(this)

    abstract fun toStringBlock(): String

    abstract fun toStringBlockChain(): String
}