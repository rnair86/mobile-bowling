package com.challenge.bowlinggame_shared

import com.challenge.bowlinggame_shared.datastructures.BiNode

public class GameFrameHelper {


    companion object{
        private val TAG = this::class.simpleName

        /***
         */
        fun GameFrame.calculateFrameTotal(nextFrame:GameFrame? =null){
            frameTotal=sumOfRolls
            if(this.isStrike || this.isSpare){
                if(this.number!=10){
                    if(nextFrame!=null){
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
                    }else{
                        frameTotal=0
                    }
                }
            }

        }


        fun BiNode<GameFrame>.calculateFrameTotal(){

            this.value.frameTotal=this.value.sumOfRolls
            if(this.value.number!=10 && (this.value.isStrike || this.value.isSpare)){
                if(this.nextNode!=null && this.nextNode?.value?.rolls?.size!! >0){
                    when{
                        this.value.isSpare->{
                            this.value.frameTotal+=this.nextNode?.value?.rolls?.first()!!
                        }
                        this.value.isStrike->{
                            if(this.nextNode?.value?.isStrike!!){
                                if(this.nextNode?.value?.number==10){
                                    if(this.nextNode?.value?.rolls?.size!! >=2){
                                        this.value.frameTotal +=(this.nextNode?.value?.rolls?.get(0)!!+this.nextNode?.value?.rolls?.get(1)!!)
                                    }else{
                                        this.value.frameTotal=0
                                    }

                                }else if(this.nextNode?.nextNode!=null && this.nextNode?.nextNode?.value?.rolls?.size!! >0){
                                    this.value.frameTotal+=(this.nextNode?.value?.rolls?.first()!! + this.nextNode?.nextNode?.value?.rolls?.first()!!)
                                }else{
                                    this.value.frameTotal=0
                                }

                            }else{
                                this.value.frameTotal+=this.nextNode?.value?.sumOfRolls!!
                            }
                        }
                    }

                }else{
                    this.value.frameTotal=0
                }
            }
        }


        /**
         * Calculates the total score of a completed frame by adding it to the score of the last completed frame
         * and sets the sumTotal
         * @param previousFrame last completed frame
         */
        fun GameFrame.calculateGameTotal(previousFrame:GameFrame?=null){
            if(this.number==1){
                sumTotal=frameTotal
            }else{
                if(previousFrame!=null){
                    if( previousFrame.hasCompleted){//this.frameTotal>0 &&
                        this.sumTotal=this.frameTotal+previousFrame.sumTotal
                    }
                }
                else{
                    this.sumTotal=0
                }
            }



        }

        fun GameFrame.setStrike(){
            this.rolls.add(10)
            this.isStrike=true
        }

        fun GameFrame.setSpare(){
            this.isSpare=true
            this.rolls.add(this.remainingPins)
        }

        fun GameFrame.setMiss(){
            this.rolls.add(0)
        }

        fun GameFrame.setRoll(roll:Int){
            this.rolls.add(roll)
        }

        fun GameFrame.isSafeToAcesssRoll(index:Int):Boolean{
            try{
                return this.rolls[index]!=null
            }
            catch (ex:Exception){
                return false
            }
        }

        fun GameFrame.canComputeSumtotal():Boolean{
            if(this.frameTotal>0){
                return true
            }
            return false
        }
    }
}