package com.challenge.bowlinggame_shared

import com.challenge.bowlinggame_shared.GameFrameExtentions.Companion.calcHasFinishedRolls
import com.challenge.bowlinggame_shared.GameFrameExtentions.Companion.getSumofRolls

class GameFrame(val id:Int) {
    var number =id
    var rolls: ArrayList<Int> = arrayListOf()
    var hasFinishedRolls=this.calcHasFinishedRolls()
    internal  var sumOfRolls = this.getSumofRolls()
    internal var frameTotal:Int=0

    var sumTotal=0
        internal set

    var isStrike=false
        private set
    var isSpare=false
        private set


}