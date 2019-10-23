package FYP.fypapp.classes

abstract class Collect {
    var c : Array<Comic>

    init {
        c = emptyArray<Comic>()
    }

    fun addMember(com : Comic) {
        c.plus(com)
    }
}