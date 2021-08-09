package com.water.song.template

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}