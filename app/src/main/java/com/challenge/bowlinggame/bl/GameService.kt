package com.challenge.bowlinggame.bl

import com.challenge.bowlinggame_shared.GameFrame
import com.challenge.bowlinggame_shared.datastructures.LinkedList

object GameService:IGameService {
    private var curretGame:LinkedList<GameFrame> = LinkedList()

    override fun getGame(newGame:Boolean): LinkedList<GameFrame> {
        if(newGame ){
            curretGame=LinkedList()
        }
        return curretGame
    }
}