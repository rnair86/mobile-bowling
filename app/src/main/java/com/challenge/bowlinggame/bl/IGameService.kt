package com.challenge.bowlinggame.bl

import com.challenge.bowlinggame_shared.GameFrame
import com.challenge.bowlinggame_shared.datastructures.LinkedList

interface IGameService {
    fun getGame(newGame:Boolean=false):LinkedList<GameFrame>
}