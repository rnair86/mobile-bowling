package com.challenge.bowlinggame.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.challenge.bowlinggame.R
import com.challenge.bowlinggame_shared.GameFrame
import com.challenge.bowlinggame_shared.GameFrameHelper.Companion.isSafeToAcesssRoll

class FrameViewHolder(inflater: LayoutInflater,parent:ViewGroup):RecyclerView.ViewHolder(inflater.inflate(R.layout.gameframe_item, parent, false)){

    private var txtframeno: TextView? = null
    private var txtframescore: TextView? = null

    private var txtroll1: TextView? = null
    private var txtroll2: TextView? = null
    private var txtroll3: TextView? = null

    init {
        txtframeno=itemView.findViewById(R.id.txtframeno)
        txtframescore=itemView.findViewById(R.id.txtframescore)

        txtroll1=itemView.findViewById(R.id.txtroll1)
        txtroll2=itemView.findViewById(R.id.txtroll2)
        txtroll3=itemView.findViewById(R.id.txtroll3)
    }

    fun bind(frame: GameFrame){
        txtframeno?.text=frame.number.toString()
        txtframescore?.text= if(frame.sumTotal>0) frame.sumTotal.toString() else ""
        if(frame.number!=10){
            disabletxtview()
        }

        when{
            (frame.number==10)->{
                when{
                    frame.isStrike->{
                        if(frame.isSafeToAcesssRoll(0)){
                            txtroll1?.text=getshowText(frame.rolls[0])
                        }
                        if(frame.isSafeToAcesssRoll(1)){
                            txtroll2?.text=getshowText(frame.rolls[1])
                        }
                        if(frame.rolls.count()>2 && frame.isSafeToAcesssRoll(2)){
                            txtroll3?.text=getshowText(frame.rolls[2])
                        }/*else{
                            disabletxtview()
                        }*/
                    }

                    frame.isSpare->{
                        if(frame.isSafeToAcesssRoll(0)){
                            txtroll1?.text=getshowText(frame.rolls[0])
                        }
                        if(frame.isSafeToAcesssRoll(1)){
                            txtroll2?.text="/"
                        }
                        if(frame.rolls.count()>2 && frame.isSafeToAcesssRoll(2)){
                            txtroll3?.text=getshowText(frame.rolls[2])
                        }/*else{
                            disabletxtview()
                        }*/
                    }
                    else->{
                        if(frame.isSafeToAcesssRoll(0)){
                            txtroll1?.text=getshowText(frame.rolls[0])
                        }
                        if(frame.isSafeToAcesssRoll(1)){
                            txtroll2?.text=getshowText(frame.rolls[1])
                        }
                    }
                }
            }
            else->{
                when{
                    frame.isStrike->{
                        txtroll1?.text="X"
                    }

                    frame.isSpare->{
                        txtroll1?.text=getshowText(frame.rolls[0])
                        txtroll2?.text="/"
                    }
                    else->{
                        if(frame.rolls.any()){
                            if(frame.isSafeToAcesssRoll(0)){
                                txtroll1?.text=getshowText(frame.rolls[0])
                            }
                            if(frame.isSafeToAcesssRoll(1)){
                                txtroll2?.text=getshowText(frame.rolls[1])
                            }
                        }


                    }
                }
            }


        }



    }

    fun getshowText(score:Int):String{
        when(score){
            0->return "-"
            in 1..9->return score.toString()
            10->return "X"
            else->return ""

        }
    }

    fun disabletxtview(){
        txtroll3?.visibility = View.GONE
    }

}