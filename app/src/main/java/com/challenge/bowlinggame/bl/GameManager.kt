package com.challenge.bowlinggame.bl

import com.challenge.bowlinggame_shared.GameFrame
import com.challenge.bowlinggame_shared.GameFrameHelper.Companion.calculateFrameTotal
import com.challenge.bowlinggame_shared.GameFrameHelper.Companion.calculateGameTotal
import com.challenge.bowlinggame_shared.GameFrameHelper.Companion.canComputeSumtotal
import com.challenge.bowlinggame_shared.GameFrameHelper.Companion.setMiss
import com.challenge.bowlinggame_shared.GameFrameHelper.Companion.setRoll
import com.challenge.bowlinggame_shared.GameFrameHelper.Companion.setSpare
import com.challenge.bowlinggame_shared.GameFrameHelper.Companion.setStrike
import com.challenge.bowlinggame_shared.datastructures.BiNode
import com.challenge.bowlinggame_shared.datastructures.LinkedList

class GameManager(override val gameStateListner: IGameStateListner) :IGameManager {

    private val GAMESERVICE:IGameService=GameService
    private lateinit var CurrentGame:LinkedList<GameFrame> //= GAMESERVICE.getGame(true)
    init {
        newGame()
    }
    override fun calculateRoll(strRoll: String) {
        //strRoll=strRoll.toUpperCase().trim()
        var currentNode = CurrentGame.lastNode()
        if(currentNode!=null){
            when{
                //Strike
                (strRoll.trimmedUpper()=="STRIKE")->{
                    if(currentNode.value.remainingPins==10){
                        currentNode.value.setStrike()
                        currentNode.prepareforNextRoll()
                    }else{
                        val message="roll cannot be $strRoll"
                        fireIllegalArgumentException(message)
                    }

                }

                //Spare
                (strRoll.trimmedUpper()=="SPARE")->{
                    if(currentNode.value.rolls.count()==1){
                        currentNode.value.setSpare()
                        currentNode.prepareforNextRoll()
                    }else{
                        val message="roll cannot be $strRoll"
                        fireIllegalArgumentException(message)
                    }

                    //makeandAddnextFrame()
                }

                //Miss
                (strRoll.trimmedUpper()=="MISS")->{
                    currentNode.value.setMiss()
                    currentNode.prepareforNextRoll()
                }

                //Integer roll
                (strRoll.isValidRollValue())->{
                    val iroll=strRoll.toInt()
                    if(iroll>currentNode.value.remainingPins){
                        val message="$strRoll cannot be greater than remaining pins"
                        fireIllegalArgumentException(message)
                    }else{
                        currentNode.value.setRoll(strRoll.toInt())
                        currentNode.prepareforNextRoll()
                    }


                }
                else->{
                    val message="$strRoll is not valid"
                    fireIllegalArgumentException(message)
                }
            }
        }

    }

    override fun getGameState(): List<GameFrame> {
        return CurrentGame.toList()
    }

    override fun getCurrentScore(): Int {
        var currentScore=0
        var currentNode = CurrentGame.lastNode()
        if(currentNode!=null){
            currentScore=currentNode?.value?.sumTotal
            if(currentScore<=0){
                while(currentNode!=null && currentScore==0){
                    currentNode=currentNode.priorNode
                    if(currentNode!=null){
                        currentScore=currentNode?.value?.sumTotal
                    }
                }
            }

        }
        return currentScore
    }

    override fun makenewGame(): List<GameFrame> {
        newGame()
        return CurrentGame.toList()
    }


    private fun makeNewFrame():GameFrame{
        return GameFrame(CurrentGame.count()+1)
    }

    private fun makeandAddnextFrame(){
        if(CurrentGame.count()<10){
            val newFrame=makeNewFrame()
            CurrentGame.append(newFrame)
        }
    }

    private fun BiNode<GameFrame>.prepareforNextRoll(){
        if(this.value.hasFinishedRolls){
            this.checkforcompletion()
            makeandAddnextFrame()
        }
        raiseGameStateUpdate()
    }

    private fun BiNode<GameFrame>.checkforcompletion(){
        if(this.value.hasFinishedRolls){
            //Frame calc
            if(this.value.isStrike || this.value.isSpare){
                if(this.value.number==10){
                    this.calculateFrameTotal()
                }else{
                    if(this.nextNode!=null && this.nextNode?.value?.rolls?.size!! >0){
                        this.calculateFrameTotal()
                    }
                }

            }else{
                this.calculateFrameTotal()
            }

            //Check Prior Node
            if(this.value.number!=1){
                if(!this.priorNode?.value?.hasCompleted!!){
                    this.priorNode?.checkforcompletion()
                }
            }

            if(this.value.number==1){

                this.value.calculateGameTotal()
            }else{
                if(this.value.canComputeSumtotal()){
                    if(this.priorNode?.value?.hasCompleted!!){
                        this.value.calculateGameTotal(this.priorNode?.value)
                    }
                }

            }




        }
    }

    private fun newGame(){
        CurrentGame =GAMESERVICE.getGame(true)
        makeandAddnextFrame()
    }

    private fun raiseGameStateUpdate(){
        gameStateListner.onGameStateUpdate(CurrentGame.toList())
    }

    private fun fireIllegalArgumentException(message:String){
        val ex=IllegalArgumentException(message)
        gameStateListner.onError(ex)
    }

    private fun String.trimmedUpper():String{
        return if(this.isNullOrBlank()) "" else this.trim().toUpperCase()
    }

    private fun String.isValidRollValue():Boolean{
       val rollVal = this.trim().toIntOrNull()
       if(rollVal!=null){
           if(rollVal in 1..9){
               return true
           }
       }
        return false
    }
}