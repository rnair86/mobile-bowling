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
        fun GameFrame.getRollsRemaining():Int{

            return if(this.rolls.size>0){
                if(this.number==10){
                    if(this.isStrike||this.isSpare){
                        3-rolls.size
                    }else{
                        2-rolls.size
                    }
                }else{
                    2-rolls.size
                }
            }else{
                2
            }

        }
        fun GameFrame.hascompleted():Boolean{
            if(this.sumTotal>0){
                return true
            }
            return false
        }
        fun GameFrame.getPinsRemaing():Int{
            val bonusTotalpins=20
            if(this.number==10 && (this.isSpare||this.isStrike)){
                return bonusTotalpins-this.sumOfRolls
            }
            return TOTALPINS - this.sumOfRolls
        }


    }
}