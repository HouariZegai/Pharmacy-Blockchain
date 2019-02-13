package blockchain.models

import blockchain.blocks.MaladyStatus

/**
 * Model of the Malady class
 */
data class Malady(val idPatient: String,
                  val idMalady: String,
                  val maladyValue: MaladyStatus)