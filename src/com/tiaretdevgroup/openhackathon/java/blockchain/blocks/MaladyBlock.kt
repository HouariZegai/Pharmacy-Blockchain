package blockchain.blocks

import org.json.JSONObject

/**
 * @Definition : Simple class to represent the Block of a Malady
 */
class MaladyBlock(number: Long,
                  val idPatient: String,
                  val idMalady: String,
                  val maladyValue: Int,
                  nonce: Int,
                  timestamp: Long,
                  previousHash: String) : Block(number, nonce, timestamp, previousHash) {

    companion object {
        const val ID_PATIENT = "idPatient"
        const val ID_MALADY = "idMalady"
        const val MALADY_VALUE = "maladyValue"
    }

    override fun toStringBlock(): String {
        val blockObject = JSONObject()
        blockObject.put(Block.NUMBER, number)
                .put(ID_PATIENT, idPatient)
                .put(ID_MALADY, idMalady)
                .put(MALADY_VALUE, maladyValue)
                .put(Block.PREVIOUS_HASH, previousHash)
                .put(NONCE, nonce)

        return blockObject.toString()
    }

    override fun toStringBlockChain(): String {
        val blockObject = JSONObject()

        blockObject.put(NUMBER, number)
                .put(ID_PATIENT, idPatient)
                .put(ID_MALADY, idMalady)
                .put(MALADY_VALUE, maladyValue)
                .put(ID_MALADY, idMalady)
                .put(HASH, hash)
                .put(NONCE, nonce)
                .put(TIMESTAMP, timestamp)
                .put(PREVIOUS_HASH, previousHash)

        return blockObject.toString()
    }

    // just for debug
    override fun toString(): String {
        return "MaladyBlock(number=$number, idPatient='$idPatient', idMalady='$idMalady', hash='$hash', maladyValue=$maladyValue, nonce=$nonce, previousHash='$previousHash')"
    }
}