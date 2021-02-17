package com.challenge.bowlinggame_shared

public class GameFrameHelper {


    companion object{
        /***
         */
        fun GameFrame.calculateFrameTotal(nextFrame:GameFrame){
            frameTotal=sumOfRolls
            if(this.isStrike || this.isSpare){
                if(nextFrame.rolls.size>0){
                    when{
                        isStrike->{
                            frameTotal += nextFrame.sumOfRolls
                        }
                        isSpare->{
                            frameTotal += nextFrame.rolls.first()

                        }
                    }

                }
            }


        }

        /**
         * Calculates the total score of a completed frame by adding it to the score of the last completed frame
         * and sets the sumTotal
         * @param previousFrame last completed frame
         */
        fun GameFrame.calculateGameTotal(previousFrame:GameFrame){
            if(this.hasFinishedRolls){
                this.sumTotal=this.frameTotal+previousFrame.sumTotal
            }

        }
    }
}