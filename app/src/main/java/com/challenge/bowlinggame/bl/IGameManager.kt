package com.challenge.bowlinggame.bl

import com.challenge.bowlinggame_shared.GameFrame

interface IGameManager {
    val gameStateListner:IGameStateListner
    fun calculateRoll(strRoll:String)
    fun getGameState():List<GameFrame>
    fun getCurrentScore():Int
    fun makenewGame():List<GameFrame>
}