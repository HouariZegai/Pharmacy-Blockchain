package blockchain.blocks

/**
 * Enum that will represent the Status of the malady
 * @param value : Sick or Normal
 */
enum class MaladyStatus(val value: Int) {
    SICK(1), NORMAL(0);
}