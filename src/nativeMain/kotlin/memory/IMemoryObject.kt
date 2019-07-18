package memory

import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.MemScope

interface IMemoryObject {
    fun setMemory(newMem: COpaquePointer)
    fun getMemory(scope: MemScope): COpaquePointer
}