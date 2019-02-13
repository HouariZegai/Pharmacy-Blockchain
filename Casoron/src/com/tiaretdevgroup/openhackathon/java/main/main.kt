package com.tiaretdevgroup.openhackathon.java.main

import java.net.InetAddress

fun main(args: Array<String>) {
    print(InetAddress.getLocalHost().hostAddress)
}