package com.challenge.bowlinggame_shared

internal class GameFrameExtentions {
    companion object{
        fun GameFrame.getSumofRolls()=if(this.rolls.size>0) this.rolls.sum() else 0
        fun GameFrame.calcHasFinishedRolls():Boolean{
            return when(this.number){
                10->{
                    when {
                        this.isSpare -> {
                            this.rolls.size==3
                        }
                        this.isStrike -> {
                            this.getSumofRolls()==20 || this.rolls.size==3
                        }
                        else -> {
                            this.rolls.size==2
                        }
                    }

                }
                else->{
                    if(this.isStrike){
                        return true
                    }
                    this.rolls.size==2
                }
            }
        }
    }
}