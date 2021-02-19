package com.challenge.bowlinggame_shared

import com.challenge.bowlinggame_shared.GameFrameExtentions.Companion.calcHasFinishedRolls
import com.challenge.bowlinggame_shared.GameFrameExtentions.Companion.getPinsRemaing
import com.challenge.bowlinggame_shared.GameFrameExtentions.Companion.getRollsRemaining
import com.challenge.bowlinggame_shared.GameFrameExtentions.Companion.getSumofRolls
import com.challenge.bowlinggame_shared.GameFrameExtentions.Companion.hascompleted

class GameFrame(id:Int) {

    internal val TOTALPINS=10

    var number =id
    var rolls: ArrayList<Int> = arrayListOf()
    val hasFinishedRolls//=this.calcHasFinishedRolls()
        get()=this.calcHasFinishedRolls()

    val remainingPins:Int//=TOTALPINS-sumOfRolls
        get()= this.getPinsRemaing()

    val remainingRolls:Int
        get() = this.getRollsRemaining()

    val hasCompleted:Boolean
        get() = this.hascompleted()

    var sumTotal=0
        internal set



    internal val sumOfRolls //= this.getSumofRolls()
        get()=this.getSumofRolls()
    internal var frameTotal:Int=0

    var isStrike=false
        internal set
    var isSpare=false
        internal set






}