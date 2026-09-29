package com.example.myapplication_multy2

class WasmPlatform : Platform {
    override val name: String = "WebAssembly (wasmJs)"
}

actual fun getPlatform(): Platform = WasmPlatform()
