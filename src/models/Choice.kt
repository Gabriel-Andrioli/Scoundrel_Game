package models

object Choice {
    private val choices = mutableListOf<Int>()

    fun setChoices(choices: MutableList<Int>) {
        this.choices.clear()
        this.choices.addAll(choices)
    }

    fun appendFirst(choice: Int) {
        this.choices.addFirst(choice)
    }

    fun popFirst(): Int {
        return choices.removeFirst()
    }
}