package memory

import kotlinx.cinterop.*

open class MemoryObject : IMemoryObject {

    private var memory: COpaquePointer? = null

    protected constructor(fromMemory: COpaquePointer) {
        memory = fromMemory.reinterpret<COpaquePointerVar>().pointed.value
    }

    override fun getMemory(scope: MemScope): COpaquePointer {
        return memory ?: throw Exception("Memory for $this was never initialized!")
    }

    override fun setMemory(newMem: COpaquePointer) {
        memory = newMem
    }

}